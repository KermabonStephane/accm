package com.accm.referential.infrastructure.persistence

import com.accm.referential.domain.model.Country
import spock.lang.Specification
import spock.lang.Subject

class CountryEntityMapperSpec extends Specification {

    @Subject
    def mapper = new CountryEntityMapperImpl()

    def "toDomain maps all fields"() {
        given:
        def entity = new CountryJpaEntity()
        entity.countryCode = 250
        entity.name = "France"
        entity.alpha2 = "FR"
        entity.alpha3 = "FRA"

        when:
        def country = mapper.toDomain(entity)

        then:
        country.countryCode() == 250
        country.name() == "France"
        country.alpha2() == "FR"
        country.alpha3() == "FRA"
    }

    def "updateEntity applies all fields"() {
        given:
        def entity = new CountryJpaEntity()

        def country = Country.builder()
                .countryCode(276)
                .name("Germany")
                .alpha2("DE")
                .alpha3("DEU")
                .build()

        when:
        mapper.updateEntity(entity, country)

        then:
        entity.countryCode == 276
        entity.name == "Germany"
        entity.alpha2 == "DE"
        entity.alpha3 == "DEU"
    }
}
