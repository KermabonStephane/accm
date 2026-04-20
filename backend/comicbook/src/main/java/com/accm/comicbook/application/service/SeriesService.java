package com.accm.comicbook.application.service;

import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.Series;
import com.accm.comicbook.domain.model.SeriesStatus;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.domain.port.out.ComicBookRepositoryPort;
import com.accm.comicbook.domain.port.out.SeriesRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SeriesService implements CreateSeriesUseCase, GetSeriesUseCase, ListSeriesUseCase,
        UpdateSeriesUseCase, DeleteSeriesUseCase, ListSeriesComicBooksUseCase {

    private final SeriesRepositoryPort seriesRepository;
    private final ComicBookRepositoryPort comicBookRepository;

    @Override
    public Series createSeries(Series series) {
        Series toSave = series.toBuilder()
                .id(UUID.randomUUID())
                .status(series.status() != null ? series.status() : SeriesStatus.ONGOING)
                .build();
        return seriesRepository.save(toSave);
    }

    @Override
    @Transactional(readOnly = true)
    public Series getSeriesById(UUID id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Series not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Series> listSeries() {
        return seriesRepository.findAll();
    }

    @Override
    public Series updateSeries(UUID id, Series update) {
        Series existing = getSeriesById(id);
        return seriesRepository.save(existing.toBuilder()
                .name(update.name())
                .status(update.status())
                .parentId(update.parentId())
                .build());
    }

    @Override
    public void deleteSeries(UUID id) {
        getSeriesById(id);
        seriesRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComicBook> listComicBooks(UUID seriesId) {
        getSeriesById(seriesId);
        return comicBookRepository.findBySeriesId(seriesId);
    }
}
