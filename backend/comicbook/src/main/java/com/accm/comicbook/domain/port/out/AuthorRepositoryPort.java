package com.accm.comicbook.domain.port.out;

import com.accm.comicbook.domain.model.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepositoryPort {
    Optional<Author> findById(UUID id);
}
