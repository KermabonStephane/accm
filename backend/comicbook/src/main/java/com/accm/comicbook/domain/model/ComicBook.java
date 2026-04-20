package com.accm.comicbook.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record ComicBook(UUID id, String title, String isbn, LocalDate date, ComicBookStatus status,
                        UUID seriesId, List<ComicBookAuthor> authors) {

    public ComicBook {
        authors = authors != null ? authors : List.of();
    }
}
