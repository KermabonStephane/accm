package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Series;
import com.accm.comicbook.domain.port.out.SeriesRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class SeriesPersistenceAdapter implements SeriesRepositoryPort {

    private final SeriesJpaRepository repository;
    private final ComicBookEntityMapper mapper;

    @Override
    public Series save(Series series) {
        SeriesJpaEntity entity = series.id() != null
                ? repository.findById(series.id()).orElseGet(SeriesJpaEntity::new)
                : new SeriesJpaEntity();
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        mapper.updateEntity(entity, series);
        if (series.parentId() != null) {
            entity.setParent(repository.getReferenceById(series.parentId()));
        } else {
            entity.setParent(null);
        }
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Series> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Series> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
