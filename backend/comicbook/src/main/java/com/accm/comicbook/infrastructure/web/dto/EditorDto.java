package com.accm.comicbook.infrastructure.web.dto;

import com.accm.comicbook.domain.model.EditorStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record EditorDto(UUID id, @NotBlank String name, EditorStatus status) {
}
