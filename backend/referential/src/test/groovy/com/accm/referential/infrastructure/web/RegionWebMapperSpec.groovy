package com.accm.referential.infrastructure.web

import com.accm.referential.domain.model.Region
import com.accm.referential.infrastructure.web.dto.RegionDto
import spock.lang.Specification
import spock.lang.Subject

class RegionWebMapperSpec extends Specification {

    @Subject
    def mapper = new RegionWebMapperImpl()

    def "toDto maps all fields"() {
        given:
        def region = Region.builder().code(2).name("Africa").build()

        when:
        def dto = mapper.toDto(region)

        then:
        dto.code() == 2
        dto.name() == "Africa"
    }

    def "toDomain maps all fields"() {
        given:
        def dto = new RegionDto(19, "Americas")

        when:
        def region = mapper.toDomain(dto)

        then:
        region.code() == 19
        region.name() == "Americas"
    }
}
