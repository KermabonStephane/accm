package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;
import com.accm.comicbook.domain.model.Series;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
interface ComicBookEntityMapper {

    @Mapping(target = "seriesId", source = "series.id")
    ComicBook toDomain(ComicBookJpaEntity entity);

    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "firstname", source = "author.firstname")
    @Mapping(target = "lastname", source = "author.lastname")
    @Mapping(target = "middleName", source = "author.middlename")
    ComicBookAuthor toAuthorDomain(ComicBookAuthorJpaEntity entity);

    Author toAuthorModel(AuthorJpaEntity entity);

    AuthorJpaEntity toEntity(Author author);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "series", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget ComicBookJpaEntity entity, ComicBook comicBook);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    ComicBookAuthorJpaEntity toEntity(ComicBookJpaEntity comicBook, AuthorJpaEntity author, AuthorRole role);

    @Mapping(target = "parentId", source = "parent.id")
    Series toDomain(SeriesJpaEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    void updateEntity(@MappingTarget SeriesJpaEntity entity, Series series);
}
