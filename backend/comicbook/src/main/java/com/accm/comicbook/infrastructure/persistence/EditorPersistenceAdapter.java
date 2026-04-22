package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Editor;
import com.accm.comicbook.domain.model.EditorStatus;
import com.accm.comicbook.domain.port.out.EditorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class EditorPersistenceAdapter implements EditorRepositoryPort {

    private final EditorJpaRepository repository;
    private final ComicBookEntityMapper mapper;

    @Override
    public Editor save(Editor editor) {
        EditorJpaEntity entity = editor.id() != null
                ? repository.findById(editor.id()).orElseGet(EditorJpaEntity::new)
                : new EditorJpaEntity();
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        mapper.updateEntity(entity, editor);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Editor> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Editor> findAllActive() {
        return repository.findByStatus(EditorStatus.ACTIVE).stream().map(mapper::toDomain).toList();
    }
}
