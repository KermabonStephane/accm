package com.accm.comicbook.application.service;

import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.domain.port.out.AuthorRepositoryPort;
import com.accm.comicbook.domain.port.out.ComicBookRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService implements ListAuthorsUseCase, GetAuthorUseCase, UpdateAuthorUseCase,
        DeleteAuthorUseCase, ListAuthorComicBooksUseCase {

    private final AuthorRepositoryPort authorRepository;
    private final ComicBookRepositoryPort comicBookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author not found: " + id));
    }

    @Override
    public Author updateAuthor(UUID id, Author update) {
        Author existing = getAuthorById(id);
        return authorRepository.save(existing.toBuilder()
                .firstname(update.firstname())
                .lastname(update.lastname())
                .middlename(update.middlename())
                .build());
    }

    @Override
    public void deleteAuthor(UUID id) {
        getAuthorById(id);
        if (authorRepository.isLinkedToComicBook(id)) {
            throw new IllegalStateException("Author is linked to a comicbook and cannot be deleted");
        }
        authorRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComicBook> listComicBooks(UUID authorId) {
        getAuthorById(authorId);
        return comicBookRepository.findByAuthorId(authorId);
    }
}
