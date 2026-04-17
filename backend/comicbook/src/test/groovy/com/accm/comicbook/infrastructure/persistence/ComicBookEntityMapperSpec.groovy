package com.accm.comicbook.infrastructure.persistence

import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.ComicBook
import com.accm.comicbook.domain.model.ComicBookAuthor
import com.accm.comicbook.domain.model.ComicBookStatus
import spock.lang.Specification

import java.time.LocalDate

class ComicBookEntityMapperSpec extends Specification {

    def "toEntity maps all scalar fields"() {
        given:
        def comicBook = ComicBook.builder()
                .id(UUID.randomUUID())
                .title("Watchmen")
                .isbn("978-1-4012-0713-1")
                .date(LocalDate.of(1987, 9, 1))
                .status(ComicBookStatus.ACTIVE)
                .authors([])
                .build()

        when:
        def entity = ComicBookEntityMapper.toEntity(comicBook)

        then:
        entity.id == comicBook.id
        entity.title == "Watchmen"
        entity.isbn == "978-1-4012-0713-1"
        entity.date == LocalDate.of(1987, 9, 1)
        entity.status == ComicBookStatus.ACTIVE
        entity.authors.isEmpty()
    }

    def "toEntity maps authors"() {
        given:
        def author = ComicBookAuthor.builder()
                .id(UUID.randomUUID())
                .firstname("Alan")
                .lastname("Moore")
                .role(AuthorRole.WRITER)
                .build()
        def comicBook = ComicBook.builder()
                .id(UUID.randomUUID())
                .title("Watchmen")
                .status(ComicBookStatus.ACTIVE)
                .authors([author])
                .build()

        when:
        def entity = ComicBookEntityMapper.toEntity(comicBook)

        then:
        entity.authors.size() == 1
        entity.authors[0].author.id == author.id
        entity.authors[0].author.firstname == "Alan"
        entity.authors[0].author.lastname == "Moore"
        entity.authors[0].role == AuthorRole.WRITER
    }

    def "toDomain maps all scalar fields"() {
        given:
        def entity = new ComicBookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Watchmen"
        entity.isbn = "978-1-4012-0713-1"
        entity.date = LocalDate.of(1987, 9, 1)
        entity.status = ComicBookStatus.ACTIVE
        entity.authors = []

        when:
        def comicBook = ComicBookEntityMapper.toDomain(entity)

        then:
        comicBook.id == entity.id
        comicBook.title == "Watchmen"
        comicBook.isbn == "978-1-4012-0713-1"
        comicBook.date == LocalDate.of(1987, 9, 1)
        comicBook.status == ComicBookStatus.ACTIVE
        comicBook.authors.isEmpty()
    }

    def "roundtrip toEntity → toDomain preserves all data"() {
        given:
        def author = ComicBookAuthor.builder()
                .id(UUID.randomUUID())
                .firstname("Dave")
                .lastname("Gibbons")
                .role(AuthorRole.ARTIST)
                .build()
        def original = ComicBook.builder()
                .id(UUID.randomUUID())
                .title("Watchmen")
                .isbn("978-1-4012-0713-1")
                .date(LocalDate.of(1987, 9, 1))
                .status(ComicBookStatus.ACTIVE)
                .authors([author])
                .build()

        when:
        def result = ComicBookEntityMapper.toDomain(ComicBookEntityMapper.toEntity(original))

        then:
        result.id == original.id
        result.title == original.title
        result.isbn == original.isbn
        result.date == original.date
        result.status == original.status
        result.authors.size() == 1
        result.authors[0].firstname == "Dave"
        result.authors[0].lastname == "Gibbons"
        result.authors[0].role == AuthorRole.ARTIST
    }
}
