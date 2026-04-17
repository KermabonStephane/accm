package com.accm.comicbook.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
public record ComicBookAuthor(
        UUID id,
        String firstname,
        String lastname,
        String middleName,
        AuthorRole role) {
}
