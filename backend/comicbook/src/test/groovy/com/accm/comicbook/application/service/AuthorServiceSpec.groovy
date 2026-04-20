package com.accm.comicbook.application.service

import com.accm.comicbook.domain.model.Author
import com.accm.comicbook.domain.model.ComicBook
import com.accm.comicbook.domain.model.ComicBookStatus
import com.accm.comicbook.domain.port.out.AuthorRepositoryPort
import com.accm.comicbook.domain.port.out.ComicBookRepositoryPort
import spock.lang.Specification

class AuthorServiceSpec extends Specification {

    AuthorRepositoryPort authorRepository = Mock()
    ComicBookRepositoryPort comicBookRepository = Mock()
    AuthorService service = new AuthorService(authorRepository, comicBookRepository)

    def authorId = UUID.randomUUID()
    def author = new Author(authorId, "Alan", "Moore", null)

    def "listAuthors returns all authors"() {
        given:
        authorRepository.findAll() >> [author]

        when:
        def result = service.listAuthors()

        then:
        result.size() == 1
        result[0].firstname == "Alan"
    }

    def "getAuthorById returns the author"() {
        given:
        authorRepository.findById(authorId) >> Optional.of(author)

        when:
        def result = service.getAuthorById(authorId)

        then:
        result.id == authorId
        result.firstname == "Alan"
    }

    def "getAuthorById throws when author not found"() {
        given:
        authorRepository.findById(authorId) >> Optional.empty()

        when:
        service.getAuthorById(authorId)

        then:
        thrown(NoSuchElementException)
    }

    def "updateAuthor updates firstname, lastname and middlename"() {
        given:
        def update = new Author(null, "Dave", "Gibbons", "Elliott")
        authorRepository.findById(authorId) >> Optional.of(author)
        authorRepository.save(_) >> { Author a -> a }

        when:
        def result = service.updateAuthor(authorId, update)

        then:
        result.id == authorId
        result.firstname == "Dave"
        result.lastname == "Gibbons"
        result.middlename == "Elliott"
    }

    def "updateAuthor throws when author not found"() {
        given:
        authorRepository.findById(authorId) >> Optional.empty()

        when:
        service.updateAuthor(authorId, new Author(null, "Dave", "Gibbons", null))

        then:
        thrown(NoSuchElementException)
    }

    def "deleteAuthor deletes when author is not linked to any comicbook"() {
        given:
        authorRepository.findById(authorId) >> Optional.of(author)
        authorRepository.isLinkedToComicBook(authorId) >> false

        when:
        service.deleteAuthor(authorId)

        then:
        1 * authorRepository.delete(authorId)
    }

    def "deleteAuthor throws when author is linked to a comicbook"() {
        given:
        authorRepository.findById(authorId) >> Optional.of(author)
        authorRepository.isLinkedToComicBook(authorId) >> true

        when:
        service.deleteAuthor(authorId)

        then:
        thrown(IllegalStateException)
        0 * authorRepository.delete(_)
    }

    def "deleteAuthor throws when author not found"() {
        given:
        authorRepository.findById(authorId) >> Optional.empty()

        when:
        service.deleteAuthor(authorId)

        then:
        thrown(NoSuchElementException)
        0 * authorRepository.delete(_)
    }

    def "listComicBooks returns comicbooks of the author"() {
        given:
        def comicBook = ComicBook.builder().id(UUID.randomUUID()).title("Watchmen").status(ComicBookStatus.ACTIVE).build()
        authorRepository.findById(authorId) >> Optional.of(author)
        comicBookRepository.findByAuthorId(authorId) >> [comicBook]

        when:
        def result = service.listComicBooks(authorId)

        then:
        result.size() == 1
        result[0].title == "Watchmen"
    }

    def "listComicBooks throws when author not found"() {
        given:
        authorRepository.findById(authorId) >> Optional.empty()

        when:
        service.listComicBooks(authorId)

        then:
        thrown(NoSuchElementException)
    }
}
