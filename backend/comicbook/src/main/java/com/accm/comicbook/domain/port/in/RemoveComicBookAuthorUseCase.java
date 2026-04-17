package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.AuthorRole;

import java.util.UUID;

public interface RemoveComicBookAuthorUseCase {
    void removeAuthor(UUID comicBookId, UUID authorId, AuthorRole role);
}
