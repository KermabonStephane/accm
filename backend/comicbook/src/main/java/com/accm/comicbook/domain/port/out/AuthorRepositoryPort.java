package com.accm.comicbook.domain.port.out;

import com.accm.comicbook.domain.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepositoryPort {
    Author save(Author author);
    Optional<Author> findById(UUID id);
    List<Author> findAll();
    boolean isLinkedToComicBook(UUID id);
    void delete(UUID id);
}
