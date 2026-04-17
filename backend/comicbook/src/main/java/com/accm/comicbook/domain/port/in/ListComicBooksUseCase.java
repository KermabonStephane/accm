package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.ComicBook;

import java.util.List;

public interface ListComicBooksUseCase {
    List<ComicBook> listComicBooks();
}
