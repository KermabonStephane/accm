package com.accm.comicbook.infrastructure.web.dto;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;
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
        @Valid List<AuthorDto> authors
) {

    public record AuthorDto(
            UUID id,
            @NotBlank String firstname,
            @NotBlank String lastname,
            String middlename,
            @NotNull AuthorRole role
    ) {
        public static AuthorDto from(ComicBookAuthor author) {
            return new AuthorDto(author.id(), author.firstname(), author.lastname(), author.middleName(), author.role());
        }
    }

    public record ComicBookAuthorRequest(
            @NotNull UUID authorId,
            @NotNull AuthorRole role
    ) {
    }

    public static ComicBookDto from(ComicBook comicBook) {
        return new ComicBookDto(
                comicBook.getId(),
                comicBook.getTitle(),
                comicBook.getIsbn(),
                comicBook.getDate(),
                comicBook.getStatus(),
                comicBook.getAuthors().stream().map(AuthorDto::from).toList()
        );
    }
}
