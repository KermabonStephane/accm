package com.accm.referential.infrastructure.persistence;

import com.accm.referential.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
interface CountryEntityMapper {

    @Mapping(target = "regionCode", source = "region.code")
    @Mapping(target = "subRegionCode", source = "subRegion.code")
    Country toDomain(CountryJpaEntity entity);

    @Mapping(target = "region", ignore = true)
    @Mapping(target = "subRegion", ignore = true)
    void updateEntity(@MappingTarget CountryJpaEntity entity, Country country);
}
