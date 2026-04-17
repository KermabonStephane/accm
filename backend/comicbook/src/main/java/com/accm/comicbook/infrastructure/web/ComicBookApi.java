package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto.AuthorDto;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto.ComicBookAuthorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "ComicBooks", description = "ComicBook collection management API")
@RequestMapping("/api/v1/comicbooks")
interface ComicBookApi {

    @GetMapping
    @Operation(summary = "List all comicBooks")
    ResponseEntity<List<ComicBookDto>> listComicBooks();

    @GetMapping("/{id}")
    @Operation(summary = "Get a comicBook by ID")
    ResponseEntity<ComicBookDto> getComicBook(@PathVariable UUID id);

    @PostMapping
    @Operation(summary = "Create a new comicBook")
    ResponseEntity<ComicBookDto> createComicBook(@RequestBody @Valid ComicBookDto request);

    @PutMapping("/{id}")
    @Operation(summary = "Update a comicBook")
    ResponseEntity<ComicBookDto> updateComicBook(@PathVariable UUID id, @RequestBody @Valid ComicBookDto request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft-delete a comicBook (marks status as DELETED)")
    void deleteComicBook(@PathVariable UUID id);

    @GetMapping("/{comicBookId}/authors")
    @Operation(summary = "List authors of a comicBook")
    ResponseEntity<List<AuthorDto>> listAuthors(@PathVariable UUID comicBookId);

    @PostMapping("/{comicBookId}/authors")
    @Operation(summary = "Add an author to a comicBook")
    ResponseEntity<AuthorDto> addAuthor(@PathVariable UUID comicBookId, @RequestBody @Valid ComicBookAuthorRequest request);

    @DeleteMapping("/{comicBookId}/authors/{authorId}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove an author role from a comicBook")
    void removeAuthor(@PathVariable UUID comicBookId, @PathVariable UUID authorId, @PathVariable AuthorRole role);
}
