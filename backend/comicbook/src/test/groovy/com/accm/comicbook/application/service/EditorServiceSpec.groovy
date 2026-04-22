package com.accm.comicbook.application.service

import com.accm.comicbook.domain.model.Editor
import com.accm.comicbook.domain.model.EditorStatus
import com.accm.comicbook.domain.port.out.EditorRepositoryPort
import spock.lang.Specification

class EditorServiceSpec extends Specification {

    EditorRepositoryPort editorRepository = Mock()
    EditorService service = new EditorService(editorRepository)

    def editorId = UUID.randomUUID()
    def editor = Editor.builder().id(editorId).name("DC Comics").status(EditorStatus.ACTIVE).build()

    def "createEditor assigns id, sets status ACTIVE and saves"() {
        given:
        editorRepository.save(_) >> { Editor e -> e }

        when:
        def result = service.createEditor(Editor.builder().name("Marvel").build())

        then:
        result.id() != null
        result.name() == "Marvel"
        result.status() == EditorStatus.ACTIVE
    }

    def "getEditorById returns active editor"() {
        given:
        editorRepository.findById(editorId) >> Optional.of(editor)

        when:
        def result = service.getEditorById(editorId)

        then:
        result.id() == editorId
        result.name() == "DC Comics"
    }

    def "getEditorById throws when not found"() {
        given:
        editorRepository.findById(editorId) >> Optional.empty()

        when:
        service.getEditorById(editorId)

        then:
        thrown(NoSuchElementException)
    }

    def "getEditorById throws when editor is deleted"() {
        given:
        def deleted = editor.toBuilder().status(EditorStatus.DELETED).build()
        editorRepository.findById(editorId) >> Optional.of(deleted)

        when:
        service.getEditorById(editorId)

        then:
        thrown(NoSuchElementException)
    }

    def "listEditors returns all active editors"() {
        given:
        editorRepository.findAllActive() >> [editor]

        when:
        def result = service.listEditors()

        then:
        result.size() == 1
        result[0].name() == "DC Comics"
    }

    def "updateEditor updates name"() {
        given:
        editorRepository.findById(editorId) >> Optional.of(editor)
        editorRepository.save(_) >> { Editor e -> e }

        when:
        def result = service.updateEditor(editorId, Editor.builder().name("DC Comics Updated").build())

        then:
        result.id() == editorId
        result.name() == "DC Comics Updated"
        result.status() == EditorStatus.ACTIVE
    }

    def "updateEditor throws when not found"() {
        given:
        editorRepository.findById(editorId) >> Optional.empty()

        when:
        service.updateEditor(editorId, Editor.builder().name("X").build())

        then:
        thrown(NoSuchElementException)
    }

    def "deleteEditor sets status to DELETED"() {
        given:
        editorRepository.findById(editorId) >> Optional.of(editor)
        editorRepository.save(_) >> { Editor e -> e }

        when:
        service.deleteEditor(editorId)

        then:
        1 * editorRepository.save({ Editor e -> e.status() == EditorStatus.DELETED })
    }

    def "deleteEditor throws when not found"() {
        given:
        editorRepository.findById(editorId) >> Optional.empty()

        when:
        service.deleteEditor(editorId)

        then:
        thrown(NoSuchElementException)
        0 * editorRepository.save(_)
    }
}
