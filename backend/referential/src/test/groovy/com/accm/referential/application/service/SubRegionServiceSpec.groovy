package com.accm.referential.application.service

import com.accm.referential.domain.model.Region
import com.accm.referential.domain.model.SubRegion
import com.accm.referential.domain.port.out.RegionRepositoryPort
import com.accm.referential.domain.port.out.SubRegionRepositoryPort
import spock.lang.Specification

class SubRegionServiceSpec extends Specification {

    SubRegionRepositoryPort subRegionRepository = Mock()
    RegionRepositoryPort regionRepository = Mock()
    SubRegionService service = new SubRegionService(subRegionRepository, regionRepository)

    def code = 14
    def regionCode = 2
    def region = Region.builder().code(regionCode).name("Africa").build()
    def subRegion = SubRegion.builder().code(code).name("Eastern Africa").regionCode(regionCode).build()

    def "createSubRegion saves when region exists"() {
        given:
        regionRepository.findByCode(regionCode) >> Optional.of(region)
        subRegionRepository.save(_) >> { SubRegion s -> s }

        when:
        def result = service.createSubRegion(SubRegion.builder().code(14).name("Eastern Africa").regionCode(regionCode).build())

        then:
        result.code() == 14
        result.name() == "Eastern Africa"
        result.regionCode() == regionCode
    }

    def "createSubRegion throws when region not found"() {
        given:
        regionRepository.findByCode(regionCode) >> Optional.empty()

        when:
        service.createSubRegion(SubRegion.builder().code(14).name("Eastern Africa").regionCode(regionCode).build())

        then:
        thrown(NoSuchElementException)
        0 * subRegionRepository.save(_)
    }

    def "getSubRegionByCode returns the sub-region"() {
        given:
        subRegionRepository.findByCode(code) >> Optional.of(subRegion)

        when:
        def result = service.getSubRegionByCode(code)

        then:
        result.code() == code
        result.name() == "Eastern Africa"
    }

    def "getSubRegionByCode throws when not found"() {
        given:
        subRegionRepository.findByCode(code) >> Optional.empty()

        when:
        service.getSubRegionByCode(code)

        then:
        thrown(NoSuchElementException)
    }

    def "listSubRegions returns all sub-regions"() {
        given:
        subRegionRepository.findAll() >> [subRegion]

        when:
        def result = service.listSubRegions()

        then:
        result.size() == 1
        result[0].name() == "Eastern Africa"
    }

    def "updateSubRegion updates the name"() {
        given:
        subRegionRepository.findByCode(code) >> Optional.of(subRegion)
        subRegionRepository.save(_) >> { SubRegion s -> s }

        when:
        def result = service.updateSubRegion(code, SubRegion.builder().name("Western Africa").regionCode(regionCode).build())

        then:
        result.code() == code
        result.name() == "Western Africa"
        result.regionCode() == regionCode
    }

    def "updateSubRegion throws when not found"() {
        given:
        subRegionRepository.findByCode(code) >> Optional.empty()

        when:
        service.updateSubRegion(code, SubRegion.builder().build())

        then:
        thrown(NoSuchElementException)
    }

    def "deleteSubRegion deletes when found"() {
        given:
        subRegionRepository.findByCode(code) >> Optional.of(subRegion)

        when:
        service.deleteSubRegion(code)

        then:
        1 * subRegionRepository.delete(code)
    }

    def "deleteSubRegion throws when not found"() {
        given:
        subRegionRepository.findByCode(code) >> Optional.empty()

        when:
        service.deleteSubRegion(code)

        then:
        thrown(NoSuchElementException)
        0 * subRegionRepository.delete(_)
    }
}
