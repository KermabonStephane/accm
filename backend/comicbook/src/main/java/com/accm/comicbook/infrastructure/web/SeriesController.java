package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto;
import com.accm.comicbook.infrastructure.web.dto.SeriesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class SeriesController implements SeriesApi {

    private final CreateSeriesUseCase createSeriesUseCase;
    private final GetSeriesUseCase getSeriesUseCase;
    private final ListSeriesUseCase listSeriesUseCase;
    private final UpdateSeriesUseCase updateSeriesUseCase;
    private final DeleteSeriesUseCase deleteSeriesUseCase;
    private final ListSeriesComicBooksUseCase listSeriesComicBooksUseCase;
    private final SeriesWebMapper seriesWebMapper;
    private final ComicBookWebMapper comicBookWebMapper;

    @Override
    public ResponseEntity<SeriesDto> createSeries(SeriesDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seriesWebMapper.toDto(createSeriesUseCase.createSeries(seriesWebMapper.toDomain(request))));
    }

    @Override
    public ResponseEntity<List<SeriesDto>> listSeries() {
        return ResponseEntity.ok(listSeriesUseCase.listSeries().stream().map(seriesWebMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<SeriesDto> getSeries(UUID id) {
        return ResponseEntity.ok(seriesWebMapper.toDto(getSeriesUseCase.getSeriesById(id)));
    }

    @Override
    public ResponseEntity<SeriesDto> updateSeries(UUID id, SeriesDto request) {
        return ResponseEntity.ok(seriesWebMapper.toDto(updateSeriesUseCase.updateSeries(id, seriesWebMapper.toDomain(request))));
    }

    @Override
    public void deleteSeries(UUID id) {
        deleteSeriesUseCase.deleteSeries(id);
    }

    @Override
    public ResponseEntity<List<ComicBookDto>> listComicBooks(UUID id) {
        return ResponseEntity.ok(
                listSeriesComicBooksUseCase.listComicBooks(id).stream().map(comicBookWebMapper::toDto).toList());
    }
}
