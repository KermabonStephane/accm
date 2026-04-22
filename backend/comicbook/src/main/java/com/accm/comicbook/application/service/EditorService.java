package com.accm.comicbook.application.service;

import com.accm.comicbook.domain.model.Editor;
import com.accm.comicbook.domain.model.EditorStatus;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.domain.port.out.EditorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EditorService implements CreateEditorUseCase, GetEditorUseCase, ListEditorsUseCase,
        UpdateEditorUseCase, DeleteEditorUseCase {

    private final EditorRepositoryPort editorRepository;

    @Override
    public Editor createEditor(Editor editor) {
        return editorRepository.save(Editor.builder()
                .id(UUID.randomUUID())
                .name(editor.name())
                .status(EditorStatus.ACTIVE)
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Editor getEditorById(UUID id) {
        return editorRepository.findById(id)
                .filter(e -> e.status() != EditorStatus.DELETED)
                .orElseThrow(() -> new NoSuchElementException("Editor not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Editor> listEditors() {
        return editorRepository.findAllActive();
    }

    @Override
    public Editor updateEditor(UUID id, Editor update) {
        Editor existing = getEditorById(id);
        return editorRepository.save(existing.toBuilder().name(update.name()).build());
    }

    @Override
    public void deleteEditor(UUID id) {
        Editor existing = getEditorById(id);
        editorRepository.save(existing.toBuilder().status(EditorStatus.DELETED).build());
    }
}
