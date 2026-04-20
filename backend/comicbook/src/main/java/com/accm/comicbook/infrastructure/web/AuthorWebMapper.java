package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.infrastructure.web.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface AuthorWebMapper {

    AuthorDto toDto(Author author);

    @Mapping(target = "id", ignore = true)
    Author toDomain(AuthorDto dto);
}
