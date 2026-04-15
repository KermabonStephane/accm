package com.accm.people.infrastructure.persistence

import com.accm.people.domain.model.PersonRole
import com.accm.people.domain.model.PersonStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@Transactional
class PersonJpaRepositorySpec extends Specification {

    @Autowired
    PersonJpaRepository repository

    def "soft delete should mark person as DELETED without removing the row"() {
        given: "a persisted person with status CREATED"
        def entity = new PersonJpaEntity()
        entity.id = UUID.randomUUID()
        entity.firstname = "Jane"
        entity.lastname = "Doe"
        entity.nickname = "jdoe"
        entity.email = "jane.doe@example.com"
        entity.status = PersonStatus.CREATED
        entity.role = PersonRole.USER
        entity.password = "hashed-password"
        repository.save(entity)

        when: "the person is soft deleted by updating status to DELETED"
        entity.status = PersonStatus.DELETED
        repository.saveAndFlush(entity)

        then: "the person still exists in the database"
        repository.findById(entity.id).present

        and: "the status is DELETED"
        repository.findById(entity.id).get().status == PersonStatus.DELETED
    }

    def "soft delete should not affect other people"() {
        given: "two persisted people"
        def first = new PersonJpaEntity(
                id: UUID.randomUUID(), firstname: "Alice", lastname: "Martin",
                email: "alice@example.com", status: PersonStatus.CREATED,
                role: PersonRole.USER, password: "pwd1")
        def second = new PersonJpaEntity(
                id: UUID.randomUUID(), firstname: "Bob", lastname: "Smith",
                email: "bob@example.com", status: PersonStatus.CREATED,
                role: PersonRole.USER, password: "pwd2")
        repository.saveAll([first, second])

        when: "only the first person is soft deleted"
        first.status = PersonStatus.DELETED
        repository.saveAndFlush(first)

        then: "the second person still has status CREATED"
        repository.findById(second.id).get().status == PersonStatus.CREATED
    }
}
