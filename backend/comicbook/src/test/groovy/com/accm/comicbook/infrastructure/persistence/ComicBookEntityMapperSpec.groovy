package com.accm.comicbook.infrastructure.persistence

import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.ComicBookStatus
import spock.lang.Specification
import spock.lang.Subject

class ComicBookEntityMapperSpec extends Specification {

    @Subject
    def mapper = new ComicBookEntityMapperImpl()

    def "toDomain maps all scalar fields"() {
        given:
        def entity = new ComicBookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Watchmen"
        entity.isbn = "978-1-4012-0713-1"
        entity.date = java.time.LocalDate.of(1987, 9, 1)
        entity.status = ComicBookStatus.ACTIVE
        entity.authors = []

        when:
        def comicBook = mapper.toDomain(entity)

        then:
        comicBook.id == entity.id
        comicBook.title == "Watchmen"
        comicBook.isbn == "978-1-4012-0713-1"
        comicBook.date == java.time.LocalDate.of(1987, 9, 1)
        comicBook.status == ComicBookStatus.ACTIVE
        comicBook.authors.isEmpty()
    }

    def "toDomain maps authors"() {
        given:
        def authorEntity = new AuthorJpaEntity()
        authorEntity.id = UUID.randomUUID()
        authorEntity.firstname = "Dave"
        authorEntity.lastname = "Gibbons"

        def comicBookAuthorEntity = new ComicBookAuthorJpaEntity()
        comicBookAuthorEntity.id = UUID.randomUUID()
        comicBookAuthorEntity.author = authorEntity
        comicBookAuthorEntity.role = AuthorRole.ARTIST

        def entity = new ComicBookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Watchmen"
        entity.status = ComicBookStatus.ACTIVE
        entity.authors = [comicBookAuthorEntity]

        when:
        def result = mapper.toDomain(entity)

        then:
        result.authors.size() == 1
        result.authors[0].id == authorEntity.id
        result.authors[0].firstname == "Dave"
        result.authors[0].lastname == "Gibbons"
        result.authors[0].role == AuthorRole.ARTIST
    }
}
