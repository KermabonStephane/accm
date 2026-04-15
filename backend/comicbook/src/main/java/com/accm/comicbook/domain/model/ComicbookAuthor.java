package com.accm.comicbook.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ComicbookAuthor {
    private final UUID id;
    private final String firstname;
    private final String lastname;
    private final String middlename;
    private final AuthorRole role;
}
