package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;

import java.util.List;
import java.util.UUID;

class ComicBookEntityMapper {

    private ComicBookEntityMapper() {
    }

    static ComicBookJpaEntity toEntity(ComicBook comicBook) {
        ComicBookJpaEntity entity = new ComicBookJpaEntity();
        entity.setId(comicBook.getId());
        entity.setTitle(comicBook.getTitle());
        entity.setIsbn(comicBook.getIsbn());
        entity.setDate(comicBook.getDate());
        entity.setStatus(comicBook.getStatus());
        List<ComicBookAuthorJpaEntity> authors = comicBook.getAuthors().stream()
                .map(a -> toAuthorEntity(a, entity))
                .toList();
        entity.setAuthors(authors);
        return entity;
    }

    static ComicBook toDomain(ComicBookJpaEntity entity) {
        return ComicBook.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .date(entity.getDate())
                .status(entity.getStatus())
                .authors(entity.getAuthors().stream()
                        .map(ComicBookEntityMapper::toAuthorDomain)
                        .toList())
                .build();
    }

    private static ComicBookAuthorJpaEntity toAuthorEntity(ComicBookAuthor author, ComicBookJpaEntity comicBook) {
        AuthorJpaEntity authorEntity = new AuthorJpaEntity();
        authorEntity.setId(author.id());
        authorEntity.setFirstname(author.firstname());
        authorEntity.setLastname(author.lastname());
        authorEntity.setMiddlename(author.middleName());

        ComicBookAuthorJpaEntity entity = new ComicBookAuthorJpaEntity();
        entity.setId(UUID.randomUUID());
        entity.setComicBook(comicBook);
        entity.setAuthor(authorEntity);
        entity.setRole(author.role());
        return entity;
    }

    static Author toAuthorModel(AuthorJpaEntity entity) {
        return new Author(entity.getId(), entity.getFirstname(), entity.getLastname(), entity.getMiddlename());
    }

    private static ComicBookAuthor toAuthorDomain(ComicBookAuthorJpaEntity entity) {
        return ComicBookAuthor.builder()
                .id(entity.getAuthor().getId())
                .firstname(entity.getAuthor().getFirstname())
                .lastname(entity.getAuthor().getLastname())
                .middleName(entity.getAuthor().getMiddlename())
                .role(entity.getRole())
                .build();
    }
}
