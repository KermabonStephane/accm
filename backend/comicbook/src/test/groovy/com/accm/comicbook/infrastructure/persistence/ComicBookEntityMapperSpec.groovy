package com.accm.comicbook.infrastructure.persistence

import com.accm.comicbook.domain.model.Author
import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.ComicBook
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
        entity.date = java.time.LocalDate.of(1987, 9, 1)
        entity.status = ComicBookStatus.ACTIVE
        entity.authors = []

        when:
        def comicBook = mapper.toDomain(entity)

        then:
        comicBook.id == entity.id
        comicBook.title == "Watchmen"
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

    def "toEntity(Author) maps all fields"() {
        given:
        def author = new Author(UUID.randomUUID(), "Alan", "Moore", "Oswald")

        when:
        def entity = mapper.toEntity(author)

        then:
        entity.id == author.id()
        entity.firstname == "Alan"
        entity.lastname == "Moore"
        entity.middlename == "Oswald"
    }

    def "updateEntity applies scalar fields to existing entity without touching authors"() {
        given:
        def entity = new ComicBookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Old Title"
        entity.authors = [new ComicBookAuthorJpaEntity()]

        def comicBook = ComicBook.builder()
                .id(entity.id)
                .title("Watchmen")
                .date(java.time.LocalDate.of(1987, 9, 1))
                .status(ComicBookStatus.ACTIVE)
                .build()

        when:
        mapper.updateEntity(entity, comicBook)

        then:
        entity.id == comicBook.id()
        entity.title == "Watchmen"
        entity.date == java.time.LocalDate.of(1987, 9, 1)
        entity.status == ComicBookStatus.ACTIVE
        entity.authors.size() == 1
    }

    def "toEntity(ComicBookJpaEntity, AuthorJpaEntity, AuthorRole) maps all fields with generated id"() {
        given:
        def comicBookEntity = new ComicBookJpaEntity()
        comicBookEntity.id = UUID.randomUUID()

        def authorEntity = new AuthorJpaEntity()
        authorEntity.id = UUID.randomUUID()

        when:
        def result = mapper.toEntity(comicBookEntity, authorEntity, AuthorRole.WRITER)

        then:
        result.id != null
        result.comicBook == comicBookEntity
        result.author == authorEntity
        result.role == AuthorRole.WRITER
    }
}
