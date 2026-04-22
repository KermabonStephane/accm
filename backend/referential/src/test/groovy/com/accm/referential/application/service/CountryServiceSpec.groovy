package com.accm.referential.application.service

import com.accm.referential.domain.model.Country
import com.accm.referential.domain.port.out.CountryRepositoryPort
import spock.lang.Specification

class CountryServiceSpec extends Specification {

    CountryRepositoryPort countryRepository = Mock()
    CountryService service = new CountryService(countryRepository)

    def countryId = UUID.randomUUID()
    def country = Country.builder().id(countryId).name("France").alpha2("FR").alpha3("FRA").countryCode(250).build()

    def "createCountry assigns id and saves"() {
        given:
        countryRepository.save(_) >> { Country c -> c }

        when:
        def result = service.createCountry(Country.builder().name("France").alpha2("FR").alpha3("FRA").countryCode(250).build())

        then:
        result.id() != null
        result.name() == "France"
        result.alpha2() == "FR"
        result.alpha3() == "FRA"
        result.countryCode() == 250
    }

    def "getCountryById returns the country"() {
        given:
        countryRepository.findById(countryId) >> Optional.of(country)

        when:
        def result = service.getCountryById(countryId)

        then:
        result.id() == countryId
        result.name() == "France"
    }

    def "getCountryById throws when not found"() {
        given:
        countryRepository.findById(countryId) >> Optional.empty()

        when:
        service.getCountryById(countryId)

        then:
        thrown(NoSuchElementException)
    }

    def "listCountries returns all countries"() {
        given:
        countryRepository.findAll() >> [country]

        when:
        def result = service.listCountries()

        then:
        result.size() == 1
        result[0].name() == "France"
    }

    def "updateCountry updates all fields"() {
        given:
        countryRepository.findById(countryId) >> Optional.of(country)
        countryRepository.save(_) >> { Country c -> c }

        when:
        def result = service.updateCountry(countryId, Country.builder().name("Germany").alpha2("DE").alpha3("DEU").countryCode(276).build())

        then:
        result.id() == countryId
        result.name() == "Germany"
        result.alpha2() == "DE"
        result.alpha3() == "DEU"
        result.countryCode() == 276
    }

    def "updateCountry throws when not found"() {
        given:
        countryRepository.findById(countryId) >> Optional.empty()

        when:
        service.updateCountry(countryId, Country.builder().build())

        then:
        thrown(NoSuchElementException)
    }

    def "deleteCountry deletes when found"() {
        given:
        countryRepository.findById(countryId) >> Optional.of(country)

        when:
        service.deleteCountry(countryId)

        then:
        1 * countryRepository.delete(countryId)
    }

    def "deleteCountry throws when not found"() {
        given:
        countryRepository.findById(countryId) >> Optional.empty()

        when:
        service.deleteCountry(countryId)

        then:
        thrown(NoSuchElementException)
        0 * countryRepository.delete(_)
    }
}
