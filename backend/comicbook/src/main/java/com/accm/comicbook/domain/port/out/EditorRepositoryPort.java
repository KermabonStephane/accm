package com.accm.comicbook.domain.port.out;

import com.accm.comicbook.domain.model.Editor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EditorRepositoryPort {
    Editor save(Editor editor);
    Optional<Editor> findById(UUID id);
    List<Editor> findAllActive();
}
