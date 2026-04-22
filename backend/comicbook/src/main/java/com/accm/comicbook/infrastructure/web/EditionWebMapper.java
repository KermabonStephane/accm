package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.Edition;
import com.accm.comicbook.infrastructure.web.dto.EditionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface EditionWebMapper {

    EditionDto toDto(Edition edition);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comicBookId", ignore = true)
    Edition toDomain(EditionDto dto);
}
