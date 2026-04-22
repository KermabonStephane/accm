package com.accm.referential.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CountryDto(
        UUID id,
        @NotBlank String name,
        @NotBlank @Size(min = 2, max = 2) String alpha2,
        @NotBlank @Size(min = 3, max = 3) String alpha3,
        @NotNull Integer countryCode
) {
}
