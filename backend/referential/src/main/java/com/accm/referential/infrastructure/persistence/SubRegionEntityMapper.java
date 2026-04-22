package com.accm.referential.infrastructure.persistence;

import com.accm.referential.domain.model.SubRegion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
interface SubRegionEntityMapper {

    @Mapping(target = "regionCode", source = "region.code")
    SubRegion toDomain(SubRegionJpaEntity entity);

    @Mapping(target = "region", ignore = true)
    void updateEntity(@MappingTarget SubRegionJpaEntity entity, SubRegion subRegion);
}
