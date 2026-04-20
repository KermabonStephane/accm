package com.accm.comicbook.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record AuthorDto(
        UUID id,
        @NotBlank String firstname,
        @NotBlank String lastname,
        String middlename
) {
}
