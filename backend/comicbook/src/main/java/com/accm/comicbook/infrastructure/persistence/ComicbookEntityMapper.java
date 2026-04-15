package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.domain.model.Comicbook;
import com.accm.comicbook.domain.model.ComicbookAuthor;

import java.util.List;
import java.util.UUID;

class ComicbookEntityMapper {

    private ComicbookEntityMapper() {
    }

    static ComicbookJpaEntity toEntity(Comicbook comicbook) {
        ComicbookJpaEntity entity = new ComicbookJpaEntity();
        entity.setId(comicbook.getId());
        entity.setTitle(comicbook.getTitle());
        entity.setIsbn(comicbook.getIsbn());
        entity.setDate(comicbook.getDate());
        entity.setStatus(comicbook.getStatus());
        entity.setAuthors(comicbook.getAuthors().stream()
                .map(ComicbookEntityMapper::toAuthorEntity)
                .toList());
        return entity;
    }

    static Comicbook toDomain(ComicbookJpaEntity entity) {
        return Comicbook.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .date(entity.getDate())
                .status(entity.getStatus())
                .authors(entity.getAuthors().stream()
                        .map(ComicbookEntityMapper::toAuthorDomain)
                        .toList())
                .build();
    }

    private static ComicbookAuthorJpaEntity toAuthorEntity(ComicbookAuthor author) {
        AuthorJpaEntity authorEntity = new AuthorJpaEntity();
        authorEntity.setId(author.getId());
        authorEntity.setFirstname(author.getFirstname());
        authorEntity.setLastname(author.getLastname());
        authorEntity.setMiddlename(author.getMiddlename());

        ComicbookAuthorJpaEntity entity = new ComicbookAuthorJpaEntity();
        entity.setId(UUID.randomUUID());
        entity.setAuthor(authorEntity);
        entity.setRole(author.getRole());
        return entity;
    }

    static Author toAuthorModel(AuthorJpaEntity entity) {
        return new Author(entity.getId(), entity.getFirstname(), entity.getLastname(), entity.getMiddlename());
    }

    private static ComicbookAuthor toAuthorDomain(ComicbookAuthorJpaEntity entity) {
        return ComicbookAuthor.builder()
                .id(entity.getAuthor().getId())
                .firstname(entity.getAuthor().getFirstname())
                .lastname(entity.getAuthor().getLastname())
                .middlename(entity.getAuthor().getMiddlename())
                .role(entity.getRole())
                .build();
    }
}
