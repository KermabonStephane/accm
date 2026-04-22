package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.Editor;
import com.accm.comicbook.infrastructure.web.dto.EditorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface EditorWebMapper {

    EditorDto toDto(Editor editor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Editor toDomain(EditorDto dto);
}
