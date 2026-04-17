package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.ComicBook;

public interface CreateComicBookUseCase {
    ComicBook createComicBook(ComicBook comicBook);
}
