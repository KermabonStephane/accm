package com.accm.people.infrastructure.persistence

import com.accm.people.domain.model.Person
import com.accm.people.domain.model.PersonRole
import com.accm.people.domain.model.PersonStatus
import spock.lang.Specification
import spock.lang.Subject

class PersonEntityMapperSpec extends Specification {

    @Subject
    def mapper = PersonEntityMapper

    def "toEntity should map all fields from domain Person to PersonJpaEntity"() {
        given:
        def person = Person.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .firstname("Jane")
                .lastname("Doe")
                .nickname("jdoe")
                .email("jane.doe@example.com")
                .status(PersonStatus.CREATED)
                .role(PersonRole.USER)
                .password("hashed-password")
                .build()

        when:
        def entity = PersonEntityMapper.toEntity(person)

        then:
        entity.id == person.id
        entity.firstname == person.firstname
        entity.lastname == person.lastname
        entity.nickname == person.nickname
        entity.email == person.email
        entity.status == person.status
        entity.role == person.role
        entity.password == person.password
    }

    def "toDomain should map all fields from PersonJpaEntity to domain Person"() {
        given:
        def entity = new PersonJpaEntity()
        entity.id = UUID.fromString("00000000-0000-0000-0000-000000000002")
        entity.firstname = "John"
        entity.lastname = "Smith"
        entity.nickname = "jsmith"
        entity.email = "john.smith@example.com"
        entity.status = PersonStatus.VALIDATED
        entity.role = PersonRole.ADMIN
        entity.password = "hashed-password"

        when:
        def person = PersonEntityMapper.toDomain(entity)

        then:
        person.id == entity.id
        person.firstname == entity.firstname
        person.lastname == entity.lastname
        person.nickname == entity.nickname
        person.email == entity.email
        person.status == entity.status
        person.role == entity.role
        person.password == entity.password
    }

    def "toEntity and toDomain should be inverse operations"() {
        given:
        def original = Person.builder()
                .id(UUID.randomUUID())
                .firstname("Alice")
                .lastname("Martin")
                .nickname("alice")
                .email("alice@example.com")
                .status(PersonStatus.CREATED)
                .role(PersonRole.USER)
                .password("secret")
                .build()

        when:
        def roundTripped = PersonEntityMapper.toDomain(PersonEntityMapper.toEntity(original))

        then:
        roundTripped.id == original.id
        roundTripped.firstname == original.firstname
        roundTripped.lastname == original.lastname
        roundTripped.nickname == original.nickname
        roundTripped.email == original.email
        roundTripped.status == original.status
        roundTripped.role == original.role
        roundTripped.password == original.password
    }
}
