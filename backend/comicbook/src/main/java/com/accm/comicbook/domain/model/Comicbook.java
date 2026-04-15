package com.accm.comicbook.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Comicbook {
    private final UUID id;
    private final String title;
    private final String isbn;
    private final LocalDate date;
    private final ComicbookStatus status;
    @Builder.Default
    private final List<ComicbookAuthor> authors = List.of();
}
