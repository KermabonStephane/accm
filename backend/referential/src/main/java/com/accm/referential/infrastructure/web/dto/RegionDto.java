package com.accm.referential.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegionDto(
        @NotNull Integer code,
        @NotBlank String name
) {
}
