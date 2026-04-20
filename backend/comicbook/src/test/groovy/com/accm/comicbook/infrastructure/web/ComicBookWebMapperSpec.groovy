package com.accm.comicbook.infrastructure.web

import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.ComicBook
import com.accm.comicbook.domain.model.ComicBookAuthor
import com.accm.comicbook.domain.model.ComicBookStatus
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class ComicBookWebMapperSpec extends Specification {

    @Subject
    def mapper = new ComicBookWebMapperImpl()

    def "toDto(ComicBook) maps all scalar fields"() {
        given:
        def comicBook = ComicBook.builder()
                .id(UUID.randomUUID())
                .title("Watchmen")
                .isbn("978-1-4012-0713-1")
                .date(LocalDate.of(1987, 9, 1))
                .status(ComicBookStatus.ACTIVE)
                .build()

        when:
        def dto = mapper.toDto(comicBook)

        then:
        dto.id() == comicBook.id
        dto.title() == "Watchmen"
        dto.isbn() == "978-1-4012-0713-1"
        dto.date() == LocalDate.of(1987, 9, 1)
        dto.status() == ComicBookStatus.ACTIVE
        dto.authors().isEmpty()
    }

    def "toDto(ComicBook) maps authors list including middleName → middlename"() {
        given:
        def author = ComicBookAuthor.builder()
                .id(UUID.randomUUID())
                .firstname("Alan")
                .lastname("Moore")
                .middleName("Oswald")
                .role(AuthorRole.WRITER)
                .build()
        def comicBook = ComicBook.builder()
                .id(UUID.randomUUID())
                .title("Watchmen")
                .status(ComicBookStatus.ACTIVE)
                .authors([author])
                .build()

        when:
        def dto = mapper.toDto(comicBook)

        then:
        dto.authors().size() == 1
        dto.authors()[0].id() == author.id()
        dto.authors()[0].firstname() == "Alan"
        dto.authors()[0].lastname() == "Moore"
        dto.authors()[0].middlename() == "Oswald"
        dto.authors()[0].role() == AuthorRole.WRITER
    }

    def "toDomain(ComicBookDto) maps scalar fields and ignores id and status"() {
        given:
        def dto = new ComicBookDto(
                UUID.randomUUID(),
                "Watchmen",
                "978-1-4012-0713-1",
                LocalDate.of(1987, 9, 1),
                ComicBookStatus.DELETED,
                null,
                null,
                null,
                []
        )

        when:
        def comicBook = mapper.toDomain(dto)

        then:
        comicBook.id == null
        comicBook.title == "Watchmen"
        comicBook.isbn == "978-1-4012-0713-1"
        comicBook.date == LocalDate.of(1987, 9, 1)
        comicBook.status == null
        comicBook.authors.isEmpty()
    }

    def "toDomain(ComicBookDto) maps authors list including middlename → middleName"() {
        given:
        def authorDto = new ComicBookDto.AuthorDto(null, "Alan", "Moore", "Oswald", AuthorRole.WRITER)
        def dto = new ComicBookDto(null, "Watchmen", null, null, null, null, null, null, [authorDto])

        when:
        def comicBook = mapper.toDomain(dto)

        then:
        comicBook.authors.size() == 1
        comicBook.authors[0].id() == null
        comicBook.authors[0].firstname() == "Alan"
        comicBook.authors[0].lastname() == "Moore"
        comicBook.authors[0].middleName() == "Oswald"
        comicBook.authors[0].role() == AuthorRole.WRITER
    }

    def "toDto(ComicBookAuthor) maps middleName → middlename"() {
        given:
        def author = ComicBookAuthor.builder()
                .id(UUID.randomUUID())
                .firstname("Dave")
                .lastname("Gibbons")
                .middleName("Elliott")
                .role(AuthorRole.ARTIST)
                .build()

        when:
        def dto = mapper.toDto(author)

        then:
        dto.id() == author.id()
        dto.firstname() == "Dave"
        dto.lastname() == "Gibbons"
        dto.middlename() == "Elliott"
        dto.role() == AuthorRole.ARTIST
    }
}
