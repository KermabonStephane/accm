package com.accm.comicbook.infrastructure.web

import com.accm.comicbook.domain.model.Editor
import com.accm.comicbook.domain.model.EditorStatus
import com.accm.comicbook.infrastructure.web.dto.EditorDto
import spock.lang.Specification
import spock.lang.Subject

class EditorWebMapperSpec extends Specification {

    @Subject
    def mapper = new EditorWebMapperImpl()

    def "toDto maps all fields"() {
        given:
        def editor = Editor.builder().id(UUID.randomUUID()).name("DC Comics").status(EditorStatus.ACTIVE).build()

        when:
        def dto = mapper.toDto(editor)

        then:
        dto.id() == editor.id()
        dto.name() == "DC Comics"
        dto.status() == EditorStatus.ACTIVE
    }

    def "toDomain maps name and ignores id and status"() {
        given:
        def dto = new EditorDto(UUID.randomUUID(), "Marvel", EditorStatus.ACTIVE)

        when:
        def editor = mapper.toDomain(dto)

        then:
        editor.id() == null
        editor.name() == "Marvel"
        editor.status() == null
    }
}
