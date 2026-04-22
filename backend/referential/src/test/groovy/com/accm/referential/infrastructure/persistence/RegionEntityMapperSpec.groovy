package com.accm.referential.infrastructure.persistence

import com.accm.referential.domain.model.Region
import spock.lang.Specification
import spock.lang.Subject

class RegionEntityMapperSpec extends Specification {

    @Subject
    def mapper = new RegionEntityMapperImpl()

    def "toDomain maps all fields"() {
        given:
        def entity = new RegionJpaEntity()
        entity.code = 2
        entity.name = "Africa"

        when:
        def region = mapper.toDomain(entity)

        then:
        region.code() == 2
        region.name() == "Africa"
    }

    def "updateEntity applies all fields"() {
        given:
        def entity = new RegionJpaEntity()
        def region = Region.builder().code(19).name("Americas").build()

        when:
        mapper.updateEntity(entity, region)

        then:
        entity.code == 19
        entity.name == "Americas"
    }
}
