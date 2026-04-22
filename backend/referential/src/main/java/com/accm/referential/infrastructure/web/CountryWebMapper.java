package com.accm.referential.infrastructure.web;

import com.accm.referential.domain.model.Country;
import com.accm.referential.infrastructure.web.dto.CountryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface CountryWebMapper {

    CountryDto toDto(Country country);

    @Mapping(target = "id", ignore = true)
    Country toDomain(CountryDto dto);
}
