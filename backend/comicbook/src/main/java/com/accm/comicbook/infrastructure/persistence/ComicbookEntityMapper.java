package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Comicbook;
import com.accm.comicbook.domain.model.ComicbookAuthor;

import java.util.List;

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
        ComicbookAuthorJpaEntity entity = new ComicbookAuthorJpaEntity();
        entity.setId(author.getId());
        entity.setName(author.getName());
        entity.setRole(author.getRole());
        return entity;
    }

    private static ComicbookAuthor toAuthorDomain(ComicbookAuthorJpaEntity entity) {
        return ComicbookAuthor.builder()
                .id(entity.getId())
                .name(entity.getName())
                .role(entity.getRole())
                .build();
    }
}
