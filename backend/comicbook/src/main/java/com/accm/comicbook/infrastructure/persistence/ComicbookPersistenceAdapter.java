package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Comicbook;
import com.accm.comicbook.domain.port.out.ComicbookRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class ComicbookPersistenceAdapter implements ComicbookRepositoryPort {

    private final ComicbookJpaRepository repository;

    @Override
    public Comicbook save(Comicbook comicbook) {
        return ComicbookEntityMapper.toDomain(
                repository.save(ComicbookEntityMapper.toEntity(comicbook)));
    }

    @Override
    public Optional<Comicbook> findById(UUID id) {
        return repository.findById(id).map(ComicbookEntityMapper::toDomain);
    }

    @Override
    public List<Comicbook> findAll() {
        return repository.findAll().stream()
                .map(ComicbookEntityMapper::toDomain)
                .toList();
    }
}
