package com.accm.comicbook.application.service;

import com.accm.comicbook.domain.model.Edition;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.domain.port.out.ComicBookRepositoryPort;
import com.accm.comicbook.domain.port.out.EditionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EditionService implements CreateEditionUseCase, GetEditionUseCase,
        ListComicBookEditionsUseCase, UpdateEditionUseCase, DeleteEditionUseCase {

    private final EditionRepositoryPort editionRepository;
    private final ComicBookRepositoryPort comicBookRepository;

    @Override
    public Edition createEdition(Edition edition) {
        comicBookRepository.findById(edition.comicBookId())
                .orElseThrow(() -> new NoSuchElementException("ComicBook not found: " + edition.comicBookId()));
        return editionRepository.save(edition.toBuilder().id(UUID.randomUUID()).build());
    }

    @Override
    @Transactional(readOnly = true)
    public Edition getEditionById(UUID id) {
        return editionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Edition not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Edition> listEditions(UUID comicBookId) {
        comicBookRepository.findById(comicBookId)
                .orElseThrow(() -> new NoSuchElementException("ComicBook not found: " + comicBookId));
        return editionRepository.findByComicBookId(comicBookId);
    }

    @Override
    public Edition updateEdition(UUID id, Edition update) {
        Edition existing = getEditionById(id);
        return editionRepository.save(existing.toBuilder()
                .isbn(update.isbn())
                .date(update.date())
                .editorId(update.editorId())
                .build());
    }

    @Override
    public void deleteEdition(UUID id) {
        getEditionById(id);
        editionRepository.delete(id);
    }
}
