package com.accm.referential.infrastructure.persistence;

import com.accm.referential.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
interface CountryEntityMapper {

    Country toDomain(CountryJpaEntity entity);

    void updateEntity(@MappingTarget CountryJpaEntity entity, Country country);
}
