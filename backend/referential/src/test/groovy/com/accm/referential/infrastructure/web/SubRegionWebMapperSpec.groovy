package com.accm.referential.infrastructure.web

import com.accm.referential.domain.model.SubRegion
import com.accm.referential.infrastructure.web.dto.SubRegionDto
import spock.lang.Specification
import spock.lang.Subject

class SubRegionWebMapperSpec extends Specification {

    @Subject
    def mapper = new SubRegionWebMapperImpl()

    def "toDto maps all fields"() {
        given:
        def subRegion = SubRegion.builder().code(14).name("Eastern Africa").regionCode(2).build()

        when:
        def dto = mapper.toDto(subRegion)

        then:
        dto.code() == 14
        dto.name() == "Eastern Africa"
        dto.regionCode() == 2
    }

    def "toDomain maps all fields"() {
        given:
        def dto = new SubRegionDto(143, "Central Asia", 142)

        when:
        def subRegion = mapper.toDomain(dto)

        then:
        subRegion.code() == 143
        subRegion.name() == "Central Asia"
        subRegion.regionCode() == 142
    }
}
