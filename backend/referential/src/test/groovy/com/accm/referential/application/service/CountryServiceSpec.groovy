package com.accm.referential.application.service

import com.accm.referential.domain.model.Country
import com.accm.referential.domain.port.out.CountryRepositoryPort
import spock.lang.Specification

class CountryServiceSpec extends Specification {

    CountryRepositoryPort countryRepository = Mock()
    CountryService service = new CountryService(countryRepository)

    def countryCode = 250
    def country = Country.builder().countryCode(countryCode).name("France").alpha2("FR").alpha3("FRA").regionCode(150).subRegionCode(155).build()

    def "createCountry saves and returns the country"() {
        given:
        countryRepository.save(_) >> { Country c -> c }

        when:
        def result = service.createCountry(Country.builder().countryCode(250).name("France").alpha2("FR").alpha3("FRA").build())

        then:
        result.countryCode() == 250
        result.name() == "France"
        result.alpha2() == "FR"
        result.alpha3() == "FRA"
    }

    def "getCountryByCode returns the country"() {
        given:
        countryRepository.findByCountryCode(countryCode) >> Optional.of(country)

        when:
        def result = service.getCountryByCode(countryCode)

        then:
        result.countryCode() == countryCode
        result.name() == "France"
    }

    def "getCountryByCode throws when not found"() {
        given:
        countryRepository.findByCountryCode(countryCode) >> Optional.empty()

        when:
        service.getCountryByCode(countryCode)

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
        countryRepository.findByCountryCode(countryCode) >> Optional.of(country)
        countryRepository.save(_) >> { Country c -> c }

        when:
        def result = service.updateCountry(countryCode, Country.builder().name("Germany").alpha2("DE").alpha3("DEU").countryCode(countryCode).regionCode(19).subRegionCode(150).build())

        then:
        result.countryCode() == countryCode
        result.name() == "Germany"
        result.alpha2() == "DE"
        result.alpha3() == "DEU"
        result.regionCode() == 19
        result.subRegionCode() == 150
    }

    def "updateCountry throws when not found"() {
        given:
        countryRepository.findByCountryCode(countryCode) >> Optional.empty()

        when:
        service.updateCountry(countryCode, Country.builder().build())

        then:
        thrown(NoSuchElementException)
    }

    def "deleteCountry deletes when found"() {
        given:
        countryRepository.findByCountryCode(countryCode) >> Optional.of(country)

        when:
        service.deleteCountry(countryCode)

        then:
        1 * countryRepository.delete(countryCode)
    }

    def "deleteCountry throws when not found"() {
        given:
        countryRepository.findByCountryCode(countryCode) >> Optional.empty()

        when:
        service.deleteCountry(countryCode)

        then:
        thrown(NoSuchElementException)
        0 * countryRepository.delete(_)
    }
}
