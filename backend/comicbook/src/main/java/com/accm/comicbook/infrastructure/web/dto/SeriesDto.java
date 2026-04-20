package com.accm.comicbook.infrastructure.web.dto;

import com.accm.comicbook.domain.model.SeriesStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record SeriesDto(
        UUID id,
        @NotBlank String name,
        SeriesStatus status,
        UUID parentId
) {
}
