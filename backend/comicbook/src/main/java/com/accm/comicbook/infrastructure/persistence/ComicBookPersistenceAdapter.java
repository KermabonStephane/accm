package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.port.out.ComicBookRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class ComicBookPersistenceAdapter implements ComicBookRepositoryPort {

    private final ComicBookJpaRepository repository;
    private final ComicBookAuthorJpaRepository comicBookAuthorRepository;
    private final AuthorJpaRepository authorRepository;

    @Override
    public ComicBook save(ComicBook comicBook) {
        return ComicBookEntityMapper.toDomain(
                repository.save(ComicBookEntityMapper.toEntity(comicBook)));
    }

    @Override
    public Optional<ComicBook> findById(UUID id) {
        return repository.findById(id).map(ComicBookEntityMapper::toDomain);
    }

    @Override
    public List<ComicBook> findAll() {
        return repository.findAll().stream()
                .map(ComicBookEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void linkAuthor(UUID comicBookId, UUID authorId, AuthorRole role) {
        ComicBookAuthorJpaEntity entity = new ComicBookAuthorJpaEntity();
        entity.setId(UUID.randomUUID());
        entity.setComicBook(repository.getReferenceById(comicBookId));
        entity.setAuthor(authorRepository.getReferenceById(authorId));
        entity.setRole(role);
        comicBookAuthorRepository.save(entity);
    }

    @Override
    public void unlinkAuthor(UUID comicBookId, UUID authorId, AuthorRole role) {
        comicBookAuthorRepository
                .findByComicBookIdAndAuthorIdAndRole(comicBookId, authorId, role)
                .ifPresent(comicBookAuthorRepository::delete);
    }
}
