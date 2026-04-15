package com.accm.comicbook.infrastructure.persistence

import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.ComicbookStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
@Transactional
class ComicbookJpaRepositorySpec extends Specification {

    @Autowired
    ComicbookJpaRepository repository

    def "soft delete should mark comicbook as DELETED without removing the row"() {
        given: "a persisted comicbook with status ACTIVE"
        def entity = new ComicbookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Watchmen"
        entity.isbn = "978-1-4012-0713-1"
        entity.date = LocalDate.of(1987, 9, 1)
        entity.status = ComicbookStatus.ACTIVE
        repository.save(entity)

        when: "the comicbook is soft deleted"
        entity.status = ComicbookStatus.DELETED
        repository.saveAndFlush(entity)

        then: "the comicbook still exists in the database"
        repository.findById(entity.id).present

        and: "the status is DELETED"
        repository.findById(entity.id).get().status == ComicbookStatus.DELETED
    }

    def "soft delete should not affect other comicbooks"() {
        given: "two persisted comicbooks"
        def first = new ComicbookJpaEntity(id: UUID.randomUUID(), title: "Watchmen", status: ComicbookStatus.ACTIVE)
        def second = new ComicbookJpaEntity(id: UUID.randomUUID(), title: "V for Vendetta", status: ComicbookStatus.ACTIVE)
        repository.saveAll([first, second])

        when: "only the first comicbook is soft deleted"
        first.status = ComicbookStatus.DELETED
        repository.saveAndFlush(first)

        then: "the second comicbook still has status ACTIVE"
        repository.findById(second.id).get().status == ComicbookStatus.ACTIVE
    }

    def "authors are persisted with the comicbook"() {
        given: "a comicbook with authors"
        def authorEntity = new AuthorJpaEntity()
        authorEntity.id = UUID.randomUUID()
        authorEntity.name = "Alan Moore"

        def author = new ComicbookAuthorJpaEntity()
        author.id = UUID.randomUUID()
        author.author = authorEntity
        author.role = AuthorRole.WRITER

        def entity = new ComicbookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Watchmen"
        entity.status = ComicbookStatus.ACTIVE
        entity.authors = [author]
        repository.save(entity)

        when:
        def found = repository.findById(entity.id).get()

        then:
        found.authors.size() == 1
        found.authors[0].author.name == "Alan Moore"
        found.authors[0].role == AuthorRole.WRITER
    }
}
