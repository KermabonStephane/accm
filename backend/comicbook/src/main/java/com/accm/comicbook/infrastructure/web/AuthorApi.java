package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.infrastructure.web.dto.AuthorDto;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Authors", description = "Author management API")
@RequestMapping("/api/v1/authors")
interface AuthorApi {

    @GetMapping
    @Operation(summary = "List all authors")
    ResponseEntity<List<AuthorDto>> listAuthors();

    @GetMapping("/{id}")
    @Operation(summary = "Get an author by ID")
    ResponseEntity<AuthorDto> getAuthor(@PathVariable UUID id);

    @PutMapping("/{id}")
    @Operation(summary = "Update an author")
    ResponseEntity<AuthorDto> updateAuthor(@PathVariable UUID id, @RequestBody @Valid AuthorDto request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an author (only if not linked to any comicbook)")
    void deleteAuthor(@PathVariable UUID id);

    @GetMapping("/{id}/comicbooks")
    @Operation(summary = "List comicbooks of an author")
    ResponseEntity<List<ComicBookDto>> listComicBooks(@PathVariable UUID id);
}
