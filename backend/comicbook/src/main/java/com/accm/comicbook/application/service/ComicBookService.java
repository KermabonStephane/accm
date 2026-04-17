package com.accm.comicbook.application.service;

import com.accm.comicbook.domain.model.*;
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
public class ComicBookService implements CreateComicBookUseCase, GetComicBookUseCase,
        ListComicBooksUseCase, UpdateComicBookUseCase, DeleteComicBookUseCase,
        ListComicBookAuthorsUseCase, AddComicBookAuthorUseCase, RemoveComicBookAuthorUseCase {

    private final ComicBookRepositoryPort comicBookRepository;
    private final AuthorRepositoryPort authorRepository;

    @Override
    public ComicBook createComicBook(ComicBook comicBook) {
        return comicBookRepository.save(comicBook);
    }

    @Override
    @Transactional(readOnly = true)
    public ComicBook getComicBookById(UUID id) {
        return comicBookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ComicBook not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComicBook> listComicBooks() {
        return comicBookRepository.findAll();
    }

    @Override
    public ComicBook updateComicBook(UUID id, ComicBook update) {
        ComicBook existing = getComicBookById(id);
        List<ComicBookAuthor> authors = update.getAuthors().stream()
                .map(a -> ComicBookAuthor.builder()
                        .id(UUID.randomUUID())
                        .firstname(a.firstname())
                        .lastname(a.lastname())
                        .middleName(a.middleName())
                        .role(a.role())
                        .build())
                .toList();
        return comicBookRepository.save(existing.toBuilder()
                .title(update.getTitle())
                .isbn(update.getIsbn())
                .date(update.getDate())
                .authors(authors)
                .build());
    }

    @Override
    public void deleteComicBook(UUID id) {
        ComicBook existing = getComicBookById(id);
        comicBookRepository.save(existing.toBuilder()
                .status(ComicBookStatus.DELETED)
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComicBookAuthor> listAuthors(UUID comicBookId) {
        return getComicBookById(comicBookId).getAuthors();
    }

    @Override
    public ComicBookAuthor addAuthor(UUID comicBookId, UUID authorId, AuthorRole role) {
        ComicBook comicBook = getComicBookById(comicBookId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author not found: " + authorId));

        boolean alreadyLinked = comicBook.getAuthors().stream()
                .anyMatch(a -> a.id().equals(authorId) && a.role() == role);
        if (alreadyLinked) {
            throw new IllegalStateException("Author already has role " + role + " on this comicBook");
        }

        comicBookRepository.linkAuthor(comicBookId, authorId, role);

        return ComicBookAuthor.builder()
                .id(author.id())
                .firstname(author.firstname())
                .lastname(author.lastname())
                .middleName(author.middlename())
                .role(role)
                .build();
    }

    @Override
    public void removeAuthor(UUID comicBookId, UUID authorId, AuthorRole role) {
        ComicBook comicBook = getComicBookById(comicBookId);

        boolean exists = comicBook.getAuthors().stream()
                .anyMatch(a -> a.id().equals(authorId) && a.role() == role);
        if (!exists) {
            throw new NoSuchElementException("Author with role " + role + " not found on this comicBook");
        }

        comicBookRepository.unlinkAuthor(comicBookId, authorId, role);
    }
}
