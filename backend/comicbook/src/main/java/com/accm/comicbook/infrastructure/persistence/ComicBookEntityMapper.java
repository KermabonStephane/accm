package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ComicBookEntityMapper {

    ComicBook toDomain(ComicBookJpaEntity entity);

    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "firstname", source = "author.firstname")
    @Mapping(target = "lastname", source = "author.lastname")
    @Mapping(target = "middleName", source = "author.middlename")
    ComicBookAuthor toAuthorDomain(ComicBookAuthorJpaEntity entity);

    Author toAuthorModel(AuthorJpaEntity entity);
}
