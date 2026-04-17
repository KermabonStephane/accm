package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.domain.port.out.AuthorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class AuthorPersistenceAdapter implements AuthorRepositoryPort {

    private final AuthorJpaRepository repository;

    @Override
    public Optional<Author> findById(UUID id) {
        return repository.findById(id).map(ComicBookEntityMapper::toAuthorModel);
    }
}
