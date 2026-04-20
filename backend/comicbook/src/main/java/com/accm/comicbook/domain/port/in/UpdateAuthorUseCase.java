package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Author;

import java.util.UUID;

public interface UpdateAuthorUseCase {
    Author updateAuthor(UUID id, Author update);
}
