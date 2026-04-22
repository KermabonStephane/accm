package com.accm.referential.infrastructure.persistence;

import com.accm.referential.domain.model.Region;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
interface RegionEntityMapper {

    Region toDomain(RegionJpaEntity entity);

    void updateEntity(@MappingTarget RegionJpaEntity entity, Region region);
}
