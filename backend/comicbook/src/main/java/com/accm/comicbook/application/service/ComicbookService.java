package com.accm.comicbook.application.service;

import com.accm.comicbook.domain.model.Comicbook;
import com.accm.comicbook.domain.model.ComicbookAuthor;
import com.accm.comicbook.domain.model.ComicbookStatus;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.domain.port.out.ComicbookRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ComicbookService implements CreateComicbookUseCase, GetComicbookUseCase,
        ListComicbooksUseCase, UpdateComicbookUseCase, DeleteComicbookUseCase {

    private final ComicbookRepositoryPort comicbookRepository;

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
                        .name(a.getName())
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
}
