package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.ComicbookAuthor;

import java.util.List;
import java.util.UUID;

public interface ListComicbookAuthorsUseCase {
    List<ComicbookAuthor> listAuthors(UUID comicbookId);
}
