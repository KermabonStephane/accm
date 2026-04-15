package com.accm.people.infrastructure.web.dto

import com.accm.people.domain.model.Person
import com.accm.people.domain.model.PersonRole
import com.accm.people.domain.model.PersonStatus
import spock.lang.Specification

class PersonResponseMapperSpec extends Specification {

    def "PersonDto.from should map all fields from domain Person"() {
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
        def dto = PersonDto.from(person)

        then:
        dto.id() == person.id
        dto.firstname() == person.firstname
        dto.lastname() == person.lastname
        dto.nickname() == person.nickname
        dto.email() == person.email
        dto.status() == person.status
        dto.role() == person.role
    }

    def "PersonDto.from should never expose the password"() {
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
        def dto = PersonDto.from(person)

        then:
        dto.password() == null
    }
}
