package com.accm.referential.application.service

import com.accm.referential.domain.model.Region
import com.accm.referential.domain.port.out.RegionRepositoryPort
import spock.lang.Specification

class RegionServiceSpec extends Specification {

    RegionRepositoryPort regionRepository = Mock()
    RegionService service = new RegionService(regionRepository)

    def code = 2
    def region = Region.builder().code(code).name("Africa").build()

    def "createRegion saves and returns the region"() {
        given:
        regionRepository.save(_) >> { Region r -> r }

        when:
        def result = service.createRegion(Region.builder().code(2).name("Africa").build())

        then:
        result.code() == 2
        result.name() == "Africa"
    }

    def "getRegionByCode returns the region"() {
        given:
        regionRepository.findByCode(code) >> Optional.of(region)

        when:
        def result = service.getRegionByCode(code)

        then:
        result.code() == code
        result.name() == "Africa"
    }

    def "getRegionByCode throws when not found"() {
        given:
        regionRepository.findByCode(code) >> Optional.empty()

        when:
        service.getRegionByCode(code)

        then:
        thrown(NoSuchElementException)
    }

    def "listRegions returns all regions"() {
        given:
        regionRepository.findAll() >> [region]

        when:
        def result = service.listRegions()

        then:
        result.size() == 1
        result[0].name() == "Africa"
    }

    def "updateRegion updates the name"() {
        given:
        regionRepository.findByCode(code) >> Optional.of(region)
        regionRepository.save(_) >> { Region r -> r }

        when:
        def result = service.updateRegion(code, Region.builder().name("Americas").build())

        then:
        result.code() == code
        result.name() == "Americas"
    }

    def "updateRegion throws when not found"() {
        given:
        regionRepository.findByCode(code) >> Optional.empty()

        when:
        service.updateRegion(code, Region.builder().build())

        then:
        thrown(NoSuchElementException)
    }

    def "deleteRegion deletes when found"() {
        given:
        regionRepository.findByCode(code) >> Optional.of(region)

        when:
        service.deleteRegion(code)

        then:
        1 * regionRepository.delete(code)
    }

    def "deleteRegion throws when not found"() {
        given:
        regionRepository.findByCode(code) >> Optional.empty()

        when:
        service.deleteRegion(code)

        then:
        thrown(NoSuchElementException)
        0 * regionRepository.delete(_)
    }
}
