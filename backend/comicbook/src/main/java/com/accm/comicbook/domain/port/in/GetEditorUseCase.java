package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Editor;

import java.util.UUID;

public interface GetEditorUseCase {
    Editor getEditorById(UUID id);
}
