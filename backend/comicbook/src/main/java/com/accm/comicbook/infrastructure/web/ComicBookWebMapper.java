package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ComicBookWebMapper {

    @Mapping(target = "middlename", source = "middleName")
    ComicBookDto.AuthorDto toDto(ComicBookAuthor author);

    ComicBookDto toDto(ComicBook comicBook);

    @Mapping(target = "middleName", source = "middlename")
    ComicBookAuthor toDomain(ComicBookDto.AuthorDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    ComicBook toDomain(ComicBookDto dto);
}
