package com.accm.people.infrastructure.web.dto

import com.accm.people.domain.model.Person
import com.accm.people.domain.model.PersonRole
import com.accm.people.domain.model.PersonStatus
import spock.lang.Specification

class PersonResponseMapperSpec extends Specification {

    def "PersonResponse.from should map all fields except password"() {
        given:
        def person = Person.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .firstname("Jane")
                .lastname("Doe")
                .nickname("jdoe")
                .email("jane.doe@example.com")
                .status(PersonStatus.VALIDATED)
                .role(PersonRole.ADMIN)
                .password("secret-password")
                .build()

        when:
        def response = PersonResponse.from(person)

        then:
        response.id() == person.id
        response.firstname() == person.firstname
        response.lastname() == person.lastname
        response.nickname() == person.nickname
        response.email() == person.email
        response.status() == person.status
        response.role() == person.role
    }

    def "PersonResponse.from should never expose the password"() {
        given:
        def person = Person.builder()
                .id(UUID.randomUUID())
                .firstname("Bob")
                .lastname("Builder")
                .nickname(null)
                .email("bob@example.com")
                .status(PersonStatus.CREATED)
                .role(PersonRole.USER)
                .password("super-secret")
                .build()

        when:
        def response = PersonResponse.from(person)

        then: "the response record has no password field"
        !response.hasProperty("password")
    }
}
