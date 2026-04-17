package com.accm.comicbook.application.service

import com.accm.comicbook.domain.model.Author
import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.ComicBook
import com.accm.comicbook.domain.model.ComicBookAuthor
import com.accm.comicbook.domain.model.ComicBookStatus
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class ComicBookMapperSpec extends Specification {

    @Subject
    def mapper = new ComicBookMapperImpl()

    def "toComicBookAuthor maps Author and role, including middlename → middleName"() {
        given:
        def author = new Author(UUID.randomUUID(), "Alan", "Moore", "Oswald")
        def role = AuthorRole.WRITER

        when:
        def result = mapper.toComicBookAuthor(author, role)

        then:
        result.id() == author.id()
        result.firstname() == "Alan"
        result.lastname() == "Moore"
        result.middleName() == "Oswald"
        result.role() == AuthorRole.WRITER
    }

    def "toComicBookAuthor maps Author without middlename"() {
        given:
        def author = new Author(UUID.randomUUID(), "Dave", "Gibbons", null)

        when:
        def result = mapper.toComicBookAuthor(author, AuthorRole.ARTIST)

        then:
        result.id() == author.id()
        result.firstname() == "Dave"
        result.lastname() == "Gibbons"
        result.middleName() == null
        result.role() == AuthorRole.ARTIST
    }

    def "applyUpdate copies title, isbn and date from update, keeps id, status and authors from existing"() {
        given:
        def existingAuthor = ComicBookAuthor.builder().id(UUID.randomUUID()).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        def existing = ComicBook.builder()
                .id(UUID.randomUUID())
                .title("Old Title")
                .isbn("111")
                .date(LocalDate.of(2000, 1, 1))
                .status(ComicBookStatus.ACTIVE)
                .authors([existingAuthor])
                .build()
        def update = ComicBook.builder()
                .title("Watchmen")
                .isbn("978-1-4012-0713-1")
                .date(LocalDate.of(1987, 9, 1))
                .build()

        when:
        def result = mapper.applyUpdate(existing, update)

        then:
        result.id() == existing.id()
        result.status() == ComicBookStatus.ACTIVE
        result.authors().size() == 1
        result.title() == "Watchmen"
        result.isbn() == "978-1-4012-0713-1"
        result.date() == LocalDate.of(1987, 9, 1)
    }
}
