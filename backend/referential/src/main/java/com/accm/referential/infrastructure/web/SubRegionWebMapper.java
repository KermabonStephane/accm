package com.accm.referential.infrastructure.web;

import com.accm.referential.domain.model.SubRegion;
import com.accm.referential.infrastructure.web.dto.SubRegionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface SubRegionWebMapper {

    SubRegionDto toDto(SubRegion subRegion);

    SubRegion toDomain(SubRegionDto dto);
}
