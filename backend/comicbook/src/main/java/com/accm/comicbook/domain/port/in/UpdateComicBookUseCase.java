package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.ComicBook;

import java.util.UUID;

public interface UpdateComicBookUseCase {
    ComicBook updateComicBook(UUID id, ComicBook comicBook);
}
