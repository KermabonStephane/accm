package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.ComicBookAuthor;

import java.util.List;
import java.util.UUID;

public interface ListComicBookAuthorsUseCase {
    List<ComicBookAuthor> listAuthors(UUID comicBookId);
}
