package com.accm.referential.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubRegionDto(
        @NotNull Integer code,
        @NotBlank String name,
        @NotNull Integer regionCode
) {
}
