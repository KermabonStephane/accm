package com.accm.referential.infrastructure.web

import com.accm.referential.domain.model.Country
import com.accm.referential.infrastructure.web.dto.CountryDto
import spock.lang.Specification
import spock.lang.Subject

class CountryWebMapperSpec extends Specification {

    @Subject
    def mapper = new CountryWebMapperImpl()

    def "toDto maps all fields"() {
        given:
        def country = Country.builder()
                .countryCode(250)
                .name("France")
                .alpha2("FR")
                .alpha3("FRA")
                .regionCode(150)
                .subRegionCode(155)
                .build()

        when:
        def dto = mapper.toDto(country)

        then:
        dto.countryCode() == 250
        dto.name() == "France"
        dto.alpha2() == "FR"
        dto.alpha3() == "FRA"
        dto.regionCode() == 150
        dto.subRegionCode() == 155
    }

    def "toDomain maps all fields"() {
        given:
        def dto = new CountryDto(276, "Germany", "DE", "DEU", 150, 155)

        when:
        def country = mapper.toDomain(dto)

        then:
        country.countryCode() == 276
        country.name() == "Germany"
        country.alpha2() == "DE"
        country.alpha3() == "DEU"
        country.regionCode() == 150
        country.subRegionCode() == 155
    }
}
