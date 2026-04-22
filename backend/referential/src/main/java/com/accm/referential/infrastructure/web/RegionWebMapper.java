package com.accm.referential.infrastructure.web;

import com.accm.referential.domain.model.Region;
import com.accm.referential.infrastructure.web.dto.RegionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface RegionWebMapper {

    RegionDto toDto(Region region);

    Region toDomain(RegionDto dto);
}
