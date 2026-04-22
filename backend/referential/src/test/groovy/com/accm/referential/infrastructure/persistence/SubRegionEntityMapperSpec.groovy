package com.accm.referential.infrastructure.persistence

import com.accm.referential.domain.model.SubRegion
import spock.lang.Specification
import spock.lang.Subject

class SubRegionEntityMapperSpec extends Specification {

    @Subject
    def mapper = new SubRegionEntityMapperImpl()

    def "toDomain maps all fields including regionCode from region.code"() {
        given:
        def regionEntity = new RegionJpaEntity()
        regionEntity.code = 2
        regionEntity.name = "Africa"

        def entity = new SubRegionJpaEntity()
        entity.code = 14
        entity.name = "Eastern Africa"
        entity.region = regionEntity

        when:
        def subRegion = mapper.toDomain(entity)

        then:
        subRegion.code() == 14
        subRegion.name() == "Eastern Africa"
        subRegion.regionCode() == 2
    }

    def "updateEntity applies code and name but ignores region"() {
        given:
        def entity = new SubRegionJpaEntity()
        def subRegion = SubRegion.builder().code(14).name("Eastern Africa").regionCode(2).build()

        when:
        mapper.updateEntity(entity, subRegion)

        then:
        entity.code == 14
        entity.name == "Eastern Africa"
        entity.region == null
    }
}
