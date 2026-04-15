package com.accm.comicbook.application.service

import com.accm.comicbook.domain.model.Author
import com.accm.comicbook.domain.model.AuthorRole
import com.accm.comicbook.domain.model.Comicbook
import com.accm.comicbook.domain.model.ComicbookAuthor
import com.accm.comicbook.domain.model.ComicbookStatus
import com.accm.comicbook.domain.port.out.AuthorRepositoryPort
import com.accm.comicbook.domain.port.out.ComicbookRepositoryPort
import spock.lang.Specification

class ComicbookAuthorServiceSpec extends Specification {

    ComicbookRepositoryPort comicbookRepository = Mock()
    AuthorRepositoryPort authorRepository = Mock()
    ComicbookService service = new ComicbookService(comicbookRepository, authorRepository)

    def comicbookId = UUID.randomUUID()
    def authorId = UUID.randomUUID()
    def author = new Author(authorId, "Alan", "Moore", null)
    def comicbook = Comicbook.builder()
            .id(comicbookId)
            .title("Watchmen")
            .status(ComicbookStatus.ACTIVE)
            .authors([])
            .build()

    def "listAuthors returns the authors of the comicbook"() {
        given:
        def existing = ComicbookAuthor.builder()
                .id(authorId).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        comicbookRepository.findById(comicbookId) >> Optional.of(comicbook.toBuilder().authors([existing]).build())

        when:
        def result = service.listAuthors(comicbookId)

        then:
        result.size() == 1
        result[0].firstname == "Alan"
        result[0].role == AuthorRole.WRITER
    }

    def "listAuthors throws when comicbook not found"() {
        given:
        comicbookRepository.findById(comicbookId) >> Optional.empty()

        when:
        service.listAuthors(comicbookId)

        then:
        thrown(NoSuchElementException)
    }

    def "addAuthor links an existing author with a role to the comicbook"() {
        given:
        comicbookRepository.findById(comicbookId) >> Optional.of(comicbook)
        authorRepository.findById(authorId) >> Optional.of(author)
        comicbookRepository.save(_) >> { Comicbook c -> c }

        when:
        def result = service.addAuthor(comicbookId, authorId, AuthorRole.WRITER)

        then:
        result.id == authorId
        result.firstname == "Alan"
        result.lastname == "Moore"
        result.role == AuthorRole.WRITER
        1 * comicbookRepository.save({ it.authors.size() == 1 })
    }

    def "addAuthor throws when author not found"() {
        given:
        comicbookRepository.findById(comicbookId) >> Optional.of(comicbook)
        authorRepository.findById(authorId) >> Optional.empty()

        when:
        service.addAuthor(comicbookId, authorId, AuthorRole.WRITER)

        then:
        thrown(NoSuchElementException)
    }

    def "addAuthor throws when author already has the same role on the comicbook"() {
        given:
        def existing = ComicbookAuthor.builder()
                .id(authorId).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        comicbookRepository.findById(comicbookId) >> Optional.of(comicbook.toBuilder().authors([existing]).build())
        authorRepository.findById(authorId) >> Optional.of(author)

        when:
        service.addAuthor(comicbookId, authorId, AuthorRole.WRITER)

        then:
        thrown(IllegalStateException)
    }

    def "addAuthor allows the same author with a different role"() {
        given:
        def existing = ComicbookAuthor.builder()
                .id(authorId).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        comicbookRepository.findById(comicbookId) >> Optional.of(comicbook.toBuilder().authors([existing]).build())
        authorRepository.findById(authorId) >> Optional.of(author)
        comicbookRepository.save(_) >> { Comicbook c -> c }

        when:
        def result = service.addAuthor(comicbookId, authorId, AuthorRole.ARTIST)

        then:
        result.role == AuthorRole.ARTIST
        1 * comicbookRepository.save({ it.authors.size() == 2 })
    }

    def "removeAuthor removes the matching author-role from the comicbook"() {
        given:
        def existing = ComicbookAuthor.builder()
                .id(authorId).firstname("Alan").lastname("Moore").role(AuthorRole.WRITER).build()
        comicbookRepository.findById(comicbookId) >> Optional.of(comicbook.toBuilder().authors([existing]).build())
        comicbookRepository.save(_) >> { Comicbook c -> c }

        when:
        service.removeAuthor(comicbookId, authorId, AuthorRole.WRITER)

        then:
        1 * comicbookRepository.save({ it.authors.isEmpty() })
    }

    def "removeAuthor throws when the author-role is not found"() {
        given:
        comicbookRepository.findById(comicbookId) >> Optional.of(comicbook)

        when:
        service.removeAuthor(comicbookId, authorId, AuthorRole.WRITER)

        then:
        thrown(NoSuchElementException)
    }
}
