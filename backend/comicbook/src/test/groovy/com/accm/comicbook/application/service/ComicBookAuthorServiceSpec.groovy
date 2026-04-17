package com.accm.comicbook.application.service

import com.accm.comicbook.domain.model.Author
import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.ComicBook
import com.accm.comicbook.domain.model.ComicBookAuthor
import com.accm.comicbook.domain.model.ComicBookStatus
import com.accm.comicbook.domain.port.out.AuthorRepositoryPort
import com.accm.comicbook.domain.port.out.ComicBookRepositoryPort
import spock.lang.Specification

class ComicBookAuthorServiceSpec extends Specification {

    ComicBookRepositoryPort comicBookRepository = Mock()
    AuthorRepositoryPort authorRepository = Mock()
    ComicBookService service = new ComicBookService(comicBookRepository, authorRepository, new ComicBookMapperImpl())

    def comicBookId = UUID.randomUUID()
    def authorId = UUID.randomUUID()
    def author = new Author(authorId, "Alan", "Moore", null)
    def comicBook = ComicBook.builder()
            .id(comicBookId)
            .title("Watchmen")
            .status(ComicBookStatus.ACTIVE)
            .authors([])
            .build()

    def "listAuthors returns the authors of the comicBook"() {
        given:
        def existing = ComicBookAuthor.builder()
                .id(authorId).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook.toBuilder().authors([existing]).build())

        when:
        def result = service.listAuthors(comicBookId)

        then:
        result.size() == 1
        result[0].firstname == "Alan"
        result[0].role == AuthorRole.WRITER
    }

    def "listAuthors throws when comicBook not found"() {
        given:
        comicBookRepository.findById(comicBookId) >> Optional.empty()

        when:
        service.listAuthors(comicBookId)

        then:
        thrown(NoSuchElementException)
    }

    def "addAuthor links an existing author with a role to the comicBook"() {
        given:
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook)
        authorRepository.findById(authorId) >> Optional.of(author)

        when:
        def result = service.addAuthor(comicBookId, authorId, AuthorRole.WRITER)

        then:
        result.id == authorId
        result.firstname == "Alan"
        result.lastname == "Moore"
        result.role == AuthorRole.WRITER
        1 * comicBookRepository.linkAuthor(comicBookId, authorId, AuthorRole.WRITER)
    }

    def "addAuthor throws when author not found"() {
        given:
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook)
        authorRepository.findById(authorId) >> Optional.empty()

        when:
        service.addAuthor(comicBookId, authorId, AuthorRole.WRITER)

        then:
        thrown(NoSuchElementException)
    }

    def "addAuthor throws when author already has the same role on the comicBook"() {
        given:
        def existing = ComicBookAuthor.builder()
                .id(authorId).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook.toBuilder().authors([existing]).build())
        authorRepository.findById(authorId) >> Optional.of(author)

        when:
        service.addAuthor(comicBookId, authorId, AuthorRole.WRITER)

        then:
        thrown(IllegalStateException)
    }

    def "addAuthor allows the same author with a different role"() {
        given:
        def existing = ComicBookAuthor.builder()
                .id(authorId).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook.toBuilder().authors([existing]).build())
        authorRepository.findById(authorId) >> Optional.of(author)

        when:
        def result = service.addAuthor(comicBookId, authorId, AuthorRole.ARTIST)

        then:
        result.role == AuthorRole.ARTIST
        1 * comicBookRepository.linkAuthor(comicBookId, authorId, AuthorRole.ARTIST)
    }

    def "removeAuthor removes the matching author-role from the comicBook"() {
        given:
        def existing = ComicBookAuthor.builder()
                .id(authorId).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook.toBuilder().authors([existing]).build())

        when:
        service.removeAuthor(comicBookId, authorId, AuthorRole.WRITER)

        then:
        1 * comicBookRepository.unlinkAuthor(comicBookId, authorId, AuthorRole.WRITER)
    }

    def "removeAuthor throws when the author-role is not found"() {
        given:
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook)

        when:
        service.removeAuthor(comicBookId, authorId, AuthorRole.WRITER)

        then:
        thrown(NoSuchElementException)
    }
}
