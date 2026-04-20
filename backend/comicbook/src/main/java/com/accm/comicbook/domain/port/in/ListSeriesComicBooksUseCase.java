package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.ComicBook;

import java.util.List;
import java.util.UUID;

public interface ListSeriesComicBooksUseCase {
    List<ComicBook> listComicBooks(UUID seriesId);
}
