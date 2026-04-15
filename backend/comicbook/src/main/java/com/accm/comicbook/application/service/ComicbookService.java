package com.accm.comicbook.application.service;

import com.accm.comicbook.domain.model.*;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.domain.port.out.AuthorRepositoryPort;
import com.accm.comicbook.domain.port.out.ComicbookRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ComicbookService implements CreateComicbookUseCase, GetComicbookUseCase,
        ListComicbooksUseCase, UpdateComicbookUseCase, DeleteComicbookUseCase,
        ListComicbookAuthorsUseCase, AddComicbookAuthorUseCase, RemoveComicbookAuthorUseCase {

    private final ComicbookRepositoryPort comicbookRepository;
    private final AuthorRepositoryPort authorRepository;

    @Override
    public Comicbook createComicbook(Comicbook comicbook) {
        return comicbookRepository.save(comicbook);
    }

    @Override
    @Transactional(readOnly = true)
    public Comicbook getComicbookById(UUID id) {
        return comicbookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Comicbook not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comicbook> listComicbooks() {
        return comicbookRepository.findAll();
    }

    @Override
    public Comicbook updateComicbook(UUID id, Comicbook update) {
        Comicbook existing = getComicbookById(id);
        List<ComicbookAuthor> authors = update.getAuthors().stream()
                .map(a -> ComicbookAuthor.builder()
                        .id(UUID.randomUUID())
                        .firstname(a.getFirstname())
                        .lastname(a.getLastname())
                        .middlename(a.getMiddlename())
                        .role(a.getRole())
                        .build())
                .toList();
        return comicbookRepository.save(existing.toBuilder()
                .title(update.getTitle())
                .isbn(update.getIsbn())
                .date(update.getDate())
                .authors(authors)
                .build());
    }

    @Override
    public void deleteComicbook(UUID id) {
        Comicbook existing = getComicbookById(id);
        comicbookRepository.save(existing.toBuilder()
                .status(ComicbookStatus.DELETED)
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComicbookAuthor> listAuthors(UUID comicbookId) {
        return getComicbookById(comicbookId).getAuthors();
    }

    @Override
    public ComicbookAuthor addAuthor(UUID comicbookId, UUID authorId, AuthorRole role) {
        Comicbook comicbook = getComicbookById(comicbookId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author not found: " + authorId));

        boolean alreadyLinked = comicbook.getAuthors().stream()
                .anyMatch(a -> a.getId().equals(authorId) && a.getRole() == role);
        if (alreadyLinked) {
            throw new IllegalStateException("Author already has role " + role + " on this comicbook");
        }

        ComicbookAuthor newAuthor = ComicbookAuthor.builder()
                .id(author.id())
                .firstname(author.firstname())
                .lastname(author.lastname())
                .middlename(author.middlename())
                .role(role)
                .build();

        List<ComicbookAuthor> authors = new ArrayList<>(comicbook.getAuthors());
        authors.add(newAuthor);
        comicbookRepository.save(comicbook.toBuilder().authors(authors).build());
        return newAuthor;
    }

    @Override
    public void removeAuthor(UUID comicbookId, UUID authorId, AuthorRole role) {
        Comicbook comicbook = getComicbookById(comicbookId);

        List<ComicbookAuthor> authors = new ArrayList<>(comicbook.getAuthors());
        boolean removed = authors.removeIf(a -> a.getId().equals(authorId) && a.getRole() == role);
        if (!removed) {
            throw new NoSuchElementException("Author with role " + role + " not found on this comicbook");
        }
        comicbookRepository.save(comicbook.toBuilder().authors(authors).build());
    }
}
