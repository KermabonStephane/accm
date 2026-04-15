package com.accm.comicbook.infrastructure.persistence

import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.Comicbook
import com.accm.comicbook.domain.model.ComicbookAuthor
import com.accm.comicbook.domain.model.ComicbookStatus
import spock.lang.Specification

import java.time.LocalDate

class ComicbookEntityMapperSpec extends Specification {

    def "toEntity maps all scalar fields"() {
        given:
        def comicbook = Comicbook.builder()
                .id(UUID.randomUUID())
                .title("Watchmen")
                .isbn("978-1-4012-0713-1")
                .date(LocalDate.of(1987, 9, 1))
                .status(ComicbookStatus.ACTIVE)
                .authors([])
                .build()

        when:
        def entity = ComicbookEntityMapper.toEntity(comicbook)

        then:
        entity.id == comicbook.id
        entity.title == "Watchmen"
        entity.isbn == "978-1-4012-0713-1"
        entity.date == LocalDate.of(1987, 9, 1)
        entity.status == ComicbookStatus.ACTIVE
        entity.authors.isEmpty()
    }

    def "toEntity maps authors"() {
        given:
        def author = ComicbookAuthor.builder()
                .id(UUID.randomUUID())
                .firstname("Alan")
                .lastname("Moore")
                .role(AuthorRole.WRITER)
                .build()
        def comicbook = Comicbook.builder()
                .id(UUID.randomUUID())
                .title("Watchmen")
                .status(ComicbookStatus.ACTIVE)
                .authors([author])
                .build()

        when:
        def entity = ComicbookEntityMapper.toEntity(comicbook)

        then:
        entity.authors.size() == 1
        entity.authors[0].author.id == author.id
        entity.authors[0].author.firstname == "Alan"
        entity.authors[0].author.lastname == "Moore"
        entity.authors[0].role == AuthorRole.WRITER
    }

    def "toDomain maps all scalar fields"() {
        given:
        def entity = new ComicbookJpaEntity()
        entity.id = UUID.randomUUID()
        entity.title = "Watchmen"
        entity.isbn = "978-1-4012-0713-1"
        entity.date = LocalDate.of(1987, 9, 1)
        entity.status = ComicbookStatus.ACTIVE
        entity.authors = []

        when:
        def comicbook = ComicbookEntityMapper.toDomain(entity)

        then:
        comicbook.id == entity.id
        comicbook.title == "Watchmen"
        comicbook.isbn == "978-1-4012-0713-1"
        comicbook.date == LocalDate.of(1987, 9, 1)
        comicbook.status == ComicbookStatus.ACTIVE
        comicbook.authors.isEmpty()
    }

    def "roundtrip toEntity → toDomain preserves all data"() {
        given:
        def author = ComicbookAuthor.builder()
                .id(UUID.randomUUID())
                .firstname("Dave")
                .lastname("Gibbons")
                .role(AuthorRole.ARTIST)
                .build()
        def original = Comicbook.builder()
                .id(UUID.randomUUID())
                .title("Watchmen")
                .isbn("978-1-4012-0713-1")
                .date(LocalDate.of(1987, 9, 1))
                .status(ComicbookStatus.ACTIVE)
                .authors([author])
                .build()

        when:
        def result = ComicbookEntityMapper.toDomain(ComicbookEntityMapper.toEntity(original))

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
