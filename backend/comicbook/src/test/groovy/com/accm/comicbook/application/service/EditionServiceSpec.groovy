package com.accm.comicbook.application.service

import com.accm.comicbook.domain.model.ComicBook
import com.accm.comicbook.domain.model.ComicBookStatus
import com.accm.comicbook.domain.model.Edition
import com.accm.comicbook.domain.port.out.ComicBookRepositoryPort
import com.accm.comicbook.domain.port.out.EditionRepositoryPort
import spock.lang.Specification

import java.time.LocalDate

class EditionServiceSpec extends Specification {

    EditionRepositoryPort editionRepository = Mock()
    ComicBookRepositoryPort comicBookRepository = Mock()
    EditionService service = new EditionService(editionRepository, comicBookRepository)

    def comicBookId = UUID.randomUUID()
    def editionId = UUID.randomUUID()
    def comicBook = ComicBook.builder().id(comicBookId).title("Watchmen").status(ComicBookStatus.ACTIVE).build()
    def edition = Edition.builder().id(editionId).comicBookId(comicBookId).isbn("978-1-4012-0713-1").date(LocalDate.of(1987, 9, 1)).build()

    def "createEdition assigns id and saves when comicbook exists"() {
        given:
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook)
        editionRepository.save(_) >> { Edition e -> e }

        when:
        def result = service.createEdition(Edition.builder().comicBookId(comicBookId).isbn("978-0-930289-23-2").build())

        then:
        result.id() != null
        result.isbn() == "978-0-930289-23-2"
        result.comicBookId() == comicBookId
    }

    def "createEdition throws when comicbook not found"() {
        given:
        comicBookRepository.findById(comicBookId) >> Optional.empty()

        when:
        service.createEdition(Edition.builder().comicBookId(comicBookId).build())

        then:
        thrown(NoSuchElementException)
        0 * editionRepository.save(_)
    }

    def "getEditionById returns the edition"() {
        given:
        editionRepository.findById(editionId) >> Optional.of(edition)

        when:
        def result = service.getEditionById(editionId)

        then:
        result.id() == editionId
        result.isbn() == "978-1-4012-0713-1"
    }

    def "getEditionById throws when not found"() {
        given:
        editionRepository.findById(editionId) >> Optional.empty()

        when:
        service.getEditionById(editionId)

        then:
        thrown(NoSuchElementException)
    }

    def "listEditions returns editions when comicbook exists"() {
        given:
        comicBookRepository.findById(comicBookId) >> Optional.of(comicBook)
        editionRepository.findByComicBookId(comicBookId) >> [edition]

        when:
        def result = service.listEditions(comicBookId)

        then:
        result.size() == 1
        result[0].isbn() == "978-1-4012-0713-1"
    }

    def "listEditions throws when comicbook not found"() {
        given:
        comicBookRepository.findById(comicBookId) >> Optional.empty()

        when:
        service.listEditions(comicBookId)

        then:
        thrown(NoSuchElementException)
    }

    def "updateEdition updates isbn, date and editorId"() {
        given:
        def editorId = UUID.randomUUID()
        editionRepository.findById(editionId) >> Optional.of(edition)
        editionRepository.save(_) >> { Edition e -> e }

        when:
        def result = service.updateEdition(editionId, Edition.builder().isbn("new-isbn").date(LocalDate.of(2000, 1, 1)).editorId(editorId).build())

        then:
        result.id() == editionId
        result.comicBookId() == comicBookId
        result.isbn() == "new-isbn"
        result.date() == LocalDate.of(2000, 1, 1)
        result.editorId() == editorId
    }

    def "updateEdition throws when not found"() {
        given:
        editionRepository.findById(editionId) >> Optional.empty()

        when:
        service.updateEdition(editionId, Edition.builder().build())

        then:
        thrown(NoSuchElementException)
    }

    def "deleteEdition deletes when found"() {
        given:
        editionRepository.findById(editionId) >> Optional.of(edition)

        when:
        service.deleteEdition(editionId)

        then:
        1 * editionRepository.delete(editionId)
    }

    def "deleteEdition throws when not found"() {
        given:
        editionRepository.findById(editionId) >> Optional.empty()

        when:
        service.deleteEdition(editionId)

        then:
        thrown(NoSuchElementException)
        0 * editionRepository.delete(_)
    }
}
