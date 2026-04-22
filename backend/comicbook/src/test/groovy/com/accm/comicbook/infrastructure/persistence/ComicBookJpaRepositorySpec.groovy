package com.accm.comicbook.infrastructure.persistence

import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.ComicBookStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
@Transactional
class ComicBookJpaRepositorySpec extends Specification {

    @Autowired
    ComicBookJpaRepository repository

    def "soft delete should mark comicBook as DELETED without removing the row"() {
        given: "a persisted comicBook with status ACTIVE"
        def entity = new ComicBookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Watchmen"
        entity.date = LocalDate.of(1987, 9, 1)
        entity.status = ComicBookStatus.ACTIVE
        repository.save(entity)

        when: "the comicBook is soft deleted"
        entity.status = ComicBookStatus.DELETED
        repository.saveAndFlush(entity)

        then: "the comicBook still exists in the database"
        repository.findById(entity.id).present

        and: "the status is DELETED"
        repository.findById(entity.id).get().status == ComicBookStatus.DELETED
    }

    def "soft delete should not affect other comicBooks"() {
        given: "two persisted comicBooks"
        def first = new ComicBookJpaEntity(id: UUID.randomUUID(), title: "Watchmen", status: ComicBookStatus.ACTIVE)
        def second = new ComicBookJpaEntity(id: UUID.randomUUID(), title: "V for Vendetta", status: ComicBookStatus.ACTIVE)
        repository.saveAll([first, second])

        when: "only the first comicBook is soft deleted"
        first.status = ComicBookStatus.DELETED
        repository.saveAndFlush(first)

        then: "the second comicBook still has status ACTIVE"
        repository.findById(second.id).get().status == ComicBookStatus.ACTIVE
    }

    def "authors are persisted with the comicBook"() {
        given: "a comicBook with authors"
        def authorEntity = new AuthorJpaEntity()
        authorEntity.id = UUID.randomUUID()
        authorEntity.firstname = "Alan"
        authorEntity.lastname = "Moore"

        def author = new ComicBookAuthorJpaEntity()
        author.id = UUID.randomUUID()
        author.author = authorEntity
        author.role = AuthorRole.WRITER

        def entity = new ComicBookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Watchmen"
        entity.status = ComicBookStatus.ACTIVE
        entity.authors = [author]
        repository.save(entity)

        when:
        def found = repository.findById(entity.id).get()

        then:
        found.authors.size() == 1
        found.authors[0].author.firstname == "Alan"
        found.authors[0].author.lastname == "Moore"
        found.authors[0].role == AuthorRole.WRITER
    }
}
