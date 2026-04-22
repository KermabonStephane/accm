package com.accm.comicbook.infrastructure.web.dto;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicBookStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ComicBookDto(
        UUID id,
        @NotBlank String title,
        String isbn,
        LocalDate date,
        ComicBookStatus status,
        UUID seriesId,
        Integer issueNumber,
        Integer volumeNumber,
        UUID editorId,
        @Valid List<AuthorDto> authors
) {

    public record AuthorDto(
            UUID id,
            @NotBlank String firstname,
            @NotBlank String lastname,
            String middlename,
            @NotNull AuthorRole role
    ) {
    }

    public record ComicBookAuthorRequest(
            @NotNull UUID authorId,
            @NotNull AuthorRole role
    ) {
    }
}
