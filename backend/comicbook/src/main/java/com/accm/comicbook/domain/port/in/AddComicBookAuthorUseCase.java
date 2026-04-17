package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicBookAuthor;

import java.util.UUID;

public interface AddComicBookAuthorUseCase {
    ComicBookAuthor addAuthor(UUID comicBookId, UUID authorId, AuthorRole role);
}
