package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
interface ComicBookEntityMapper {

    ComicBook toDomain(ComicBookJpaEntity entity);

    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "firstname", source = "author.firstname")
    @Mapping(target = "lastname", source = "author.lastname")
    @Mapping(target = "middleName", source = "author.middlename")
    ComicBookAuthor toAuthorDomain(ComicBookAuthorJpaEntity entity);

    Author toAuthorModel(AuthorJpaEntity entity);

    AuthorJpaEntity toEntity(Author author);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget ComicBookJpaEntity entity, ComicBook comicBook);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    ComicBookAuthorJpaEntity toEntity(ComicBookJpaEntity comicBook, AuthorJpaEntity author, AuthorRole role);
}
