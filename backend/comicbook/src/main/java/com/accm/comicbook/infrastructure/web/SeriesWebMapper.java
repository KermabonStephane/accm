package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.Series;
import com.accm.comicbook.infrastructure.web.dto.SeriesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface SeriesWebMapper {

    SeriesDto toDto(Series series);

    @Mapping(target = "id", ignore = true)
    Series toDomain(SeriesDto dto);
}
