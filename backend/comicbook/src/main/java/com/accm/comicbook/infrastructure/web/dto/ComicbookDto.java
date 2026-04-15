package com.accm.comicbook.infrastructure.web.dto;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.Comicbook;
import com.accm.comicbook.domain.model.ComicbookAuthor;
import com.accm.comicbook.domain.model.ComicbookStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ComicbookDto(
        UUID id,
        @NotBlank String title,
        String isbn,
        LocalDate date,
        ComicbookStatus status,
        @Valid List<AuthorDto> authors
) {

    public record AuthorDto(
            UUID id,
            @NotBlank String name,
            @NotNull AuthorRole role
    ) {
        static AuthorDto from(ComicbookAuthor author) {
            return new AuthorDto(author.getId(), author.getName(), author.getRole());
        }
    }

    public static ComicbookDto from(Comicbook comicbook) {
        return new ComicbookDto(
                comicbook.getId(),
                comicbook.getTitle(),
                comicbook.getIsbn(),
                comicbook.getDate(),
                comicbook.getStatus(),
                comicbook.getAuthors().stream().map(AuthorDto::from).toList()
        );
    }
}
