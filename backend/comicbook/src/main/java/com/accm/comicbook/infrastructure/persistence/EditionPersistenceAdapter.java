package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Edition;
import com.accm.comicbook.domain.port.out.EditionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class EditionPersistenceAdapter implements EditionRepositoryPort {

    private final EditionJpaRepository repository;
    private final ComicBookJpaRepository comicBookRepository;
    private final EditorJpaRepository editorRepository;
    private final ComicBookEntityMapper mapper;

    @Override
    public Edition save(Edition edition) {
        EditionJpaEntity entity = edition.id() != null
                ? repository.findById(edition.id()).orElseGet(EditionJpaEntity::new)
                : new EditionJpaEntity();
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        mapper.updateEntity(entity, edition);
        entity.setComicBook(comicBookRepository.getReferenceById(edition.comicBookId()));
        entity.setEditor(edition.editorId() != null ? editorRepository.getReferenceById(edition.editorId()) : null);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Edition> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Edition> findByComicBookId(UUID comicBookId) {
        return repository.findByComicBook_Id(comicBookId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
