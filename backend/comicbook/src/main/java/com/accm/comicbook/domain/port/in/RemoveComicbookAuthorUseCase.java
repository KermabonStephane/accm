package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.AuthorRole;

import java.util.UUID;

public interface RemoveComicbookAuthorUseCase {
    void removeAuthor(UUID comicbookId, UUID authorId, AuthorRole role);
}
