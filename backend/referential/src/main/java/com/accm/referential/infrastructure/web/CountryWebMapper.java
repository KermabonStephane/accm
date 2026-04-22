package com.accm.referential.infrastructure.web;

import com.accm.referential.domain.model.Country;
import com.accm.referential.infrastructure.web.dto.CountryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface CountryWebMapper {

    CountryDto toDto(Country country);

    Country toDomain(CountryDto dto);
}
