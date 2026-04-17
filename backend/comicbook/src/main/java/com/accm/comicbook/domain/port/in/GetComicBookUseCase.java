package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.ComicBook;

import java.util.UUID;

public interface GetComicBookUseCase {
    ComicBook getComicBookById(UUID id);
}
