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
                .id(UUID.randomUUID())
                .name("France")
                .alpha2("FR")
                .alpha3("FRA")
                .countryCode(250)
                .build()

        when:
        def dto = mapper.toDto(country)

        then:
        dto.id() == country.id()
        dto.name() == "France"
        dto.alpha2() == "FR"
        dto.alpha3() == "FRA"
        dto.countryCode() == 250
    }

    def "toDomain maps fields and ignores id"() {
        given:
        def dto = new CountryDto(UUID.randomUUID(), "Germany", "DE", "DEU", 276)

        when:
        def country = mapper.toDomain(dto)

        then:
        country.id() == null
        country.name() == "Germany"
        country.alpha2() == "DE"
        country.alpha3() == "DEU"
        country.countryCode() == 276
    }
}
