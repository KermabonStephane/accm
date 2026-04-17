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
    private final ComicBookEntityMapper mapper;

    @Override
    public Author save(Author author) {
        AuthorJpaEntity entity = new AuthorJpaEntity();
        entity.setId(author.id() != null ? author.id() : UUID.randomUUID());
        entity.setFirstname(author.firstname());
        entity.setLastname(author.lastname());
        entity.setMiddlename(author.middlename());
        return mapper.toAuthorModel(repository.save(entity));
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return repository.findById(id).map(mapper::toAuthorModel);
    }
}
