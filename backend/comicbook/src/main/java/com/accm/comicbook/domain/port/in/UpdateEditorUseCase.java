package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Editor;

import java.util.UUID;

public interface UpdateEditorUseCase {
    Editor updateEditor(UUID id, Editor update);
}
