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
        entity.id = UUID.randomUUID()
        entity.name = "France"
        entity.alpha2 = "FR"
        entity.alpha3 = "FRA"
        entity.countryCode = 250

        when:
        def country = mapper.toDomain(entity)

        then:
        country.id() == entity.id
        country.name() == "France"
        country.alpha2() == "FR"
        country.alpha3() == "FRA"
        country.countryCode() == 250
    }

    def "updateEntity applies all fields"() {
        given:
        def entity = new CountryJpaEntity()
        entity.id = UUID.randomUUID()

        def country = Country.builder()
                .id(entity.id)
                .name("Germany")
                .alpha2("DE")
                .alpha3("DEU")
                .countryCode(276)
                .build()

        when:
        mapper.updateEntity(entity, country)

        then:
        entity.name == "Germany"
        entity.alpha2 == "DE"
        entity.alpha3 == "DEU"
        entity.countryCode == 276
    }
}
