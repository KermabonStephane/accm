package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicbookAuthor;

import java.util.UUID;

public interface AddComicbookAuthorUseCase {
    ComicbookAuthor addAuthor(UUID comicbookId, UUID authorId, AuthorRole role);
}
