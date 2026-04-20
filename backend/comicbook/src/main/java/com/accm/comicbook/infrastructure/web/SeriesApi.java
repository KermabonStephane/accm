package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.infrastructure.web.dto.ComicBookDto;
import com.accm.comicbook.infrastructure.web.dto.SeriesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Series", description = "Series management API")
@RequestMapping("/api/v1/series")
interface SeriesApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new series")
    ResponseEntity<SeriesDto> createSeries(@RequestBody @Valid SeriesDto request);

    @GetMapping
    @Operation(summary = "List all series")
    ResponseEntity<List<SeriesDto>> listSeries();

    @GetMapping("/{id}")
    @Operation(summary = "Get a series by ID")
    ResponseEntity<SeriesDto> getSeries(@PathVariable UUID id);

    @PutMapping("/{id}")
    @Operation(summary = "Update a series")
    ResponseEntity<SeriesDto> updateSeries(@PathVariable UUID id, @RequestBody @Valid SeriesDto request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a series")
    void deleteSeries(@PathVariable UUID id);

    @GetMapping("/{id}/comicbooks")
    @Operation(summary = "List comicbooks of a series")
    ResponseEntity<List<ComicBookDto>> listComicBooks(@PathVariable UUID id);
}
