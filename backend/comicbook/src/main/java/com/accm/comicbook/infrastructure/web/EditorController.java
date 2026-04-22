package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.infrastructure.web.dto.EditorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class EditorController implements EditorApi {

    private final CreateEditorUseCase createEditorUseCase;
    private final GetEditorUseCase getEditorUseCase;
    private final ListEditorsUseCase listEditorsUseCase;
    private final UpdateEditorUseCase updateEditorUseCase;
    private final DeleteEditorUseCase deleteEditorUseCase;
    private final EditorWebMapper editorWebMapper;

    @Override
    public ResponseEntity<EditorDto> createEditor(EditorDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(editorWebMapper.toDto(createEditorUseCase.createEditor(editorWebMapper.toDomain(request))));
    }

    @Override
    public ResponseEntity<List<EditorDto>> listEditors() {
        return ResponseEntity.ok(listEditorsUseCase.listEditors().stream().map(editorWebMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<EditorDto> getEditor(UUID id) {
        return ResponseEntity.ok(editorWebMapper.toDto(getEditorUseCase.getEditorById(id)));
    }

    @Override
    public ResponseEntity<EditorDto> updateEditor(UUID id, EditorDto request) {
        return ResponseEntity.ok(editorWebMapper.toDto(updateEditorUseCase.updateEditor(id, editorWebMapper.toDomain(request))));
    }

    @Override
    public void deleteEditor(UUID id) {
        deleteEditorUseCase.deleteEditor(id);
    }
}
