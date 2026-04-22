package com.accm.comicbook.infrastructure.web

import com.accm.comicbook.domain.model.Edition
import com.accm.comicbook.infrastructure.web.dto.EditionDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class EditionWebMapperSpec extends Specification {

    @Subject
    def mapper = new EditionWebMapperImpl()

    def "toDto maps all fields"() {
        given:
        def editorId = UUID.randomUUID()
        def edition = Edition.builder()
                .id(UUID.randomUUID())
                .comicBookId(UUID.randomUUID())
                .isbn("978-1-4012-0713-1")
                .date(LocalDate.of(1987, 9, 1))
                .editorId(editorId)
                .build()

        when:
        def dto = mapper.toDto(edition)

        then:
        dto.id() == edition.id()
        dto.isbn() == "978-1-4012-0713-1"
        dto.date() == LocalDate.of(1987, 9, 1)
        dto.editorId() == editorId
    }

    def "toDomain maps fields and ignores id and comicBookId"() {
        given:
        def dto = new EditionDto(UUID.randomUUID(), "978-0-930289-23-2", LocalDate.of(2000, 6, 1), null)

        when:
        def edition = mapper.toDomain(dto)

        then:
        edition.id() == null
        edition.comicBookId() == null
        edition.isbn() == "978-0-930289-23-2"
        edition.date() == LocalDate.of(2000, 6, 1)
        edition.editorId() == null
    }
}
