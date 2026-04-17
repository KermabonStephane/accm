package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;
import com.accm.comicbook.domain.model.ComicBookStatus;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto.AuthorDto;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto.ComicBookAuthorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comicbooks")
@RequiredArgsConstructor
@Tag(name = "ComicBooks", description = "ComicBook collection management API")
class ComicBookController {

    private final CreateComicBookUseCase createComicBookUseCase;
    private final GetComicBookUseCase getComicBookUseCase;
    private final ListComicBooksUseCase listComicBooksUseCase;
    private final UpdateComicBookUseCase updateComicBookUseCase;
    private final DeleteComicBookUseCase deleteComicBookUseCase;
    private final ListComicBookAuthorsUseCase listComicBookAuthorsUseCase;
    private final AddComicBookAuthorUseCase addComicBookAuthorUseCase;
    private final RemoveComicBookAuthorUseCase removeComicBookAuthorUseCase;
    private final ComicBookWebMapper webMapper;

    @GetMapping
    @Operation(summary = "List all comicBooks")
    ResponseEntity<List<ComicBookDto>> listComicBooks() {
        return ResponseEntity.ok(
                listComicBooksUseCase.listComicBooks().stream().map(webMapper::toDto).toList()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a comicBook by ID")
    ResponseEntity<ComicBookDto> getComicBook(@PathVariable UUID id) {
        return ResponseEntity.ok(webMapper.toDto(getComicBookUseCase.getComicBookById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new comicBook")
    ResponseEntity<ComicBookDto> createComicBook(@RequestBody @Valid ComicBookDto request) {
        ComicBook comicBook = webMapper.toDomain(request).toBuilder()
                .id(UUID.randomUUID())
                .status(ComicBookStatus.ACTIVE)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(webMapper.toDto(createComicBookUseCase.createComicBook(comicBook)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a comicBook")
    ResponseEntity<ComicBookDto> updateComicBook(@PathVariable UUID id,
                                                 @RequestBody @Valid ComicBookDto request) {
        return ResponseEntity.ok(
                webMapper.toDto(updateComicBookUseCase.updateComicBook(id, webMapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft-delete a comicBook (marks status as DELETED)")
    void deleteComicBook(@PathVariable UUID id) {
        deleteComicBookUseCase.deleteComicBook(id);
    }

    @GetMapping("/{comicBookId}/authors")
    @Operation(summary = "List authors of a comicBook")
    ResponseEntity<List<AuthorDto>> listAuthors(@PathVariable UUID comicBookId) {
        return ResponseEntity.ok(
                listComicBookAuthorsUseCase.listAuthors(comicBookId).stream()
                        .map(webMapper::toDto)
                        .toList()
        );
    }

    @PostMapping("/{comicBookId}/authors")
    @Operation(summary = "Add an author to a comicBook")
    ResponseEntity<AuthorDto> addAuthor(@PathVariable UUID comicBookId,
                                        @RequestBody @Valid ComicBookAuthorRequest request) {
        ComicBookAuthor author = addComicBookAuthorUseCase.addAuthor(comicBookId, request.authorId(), request.role());
        return ResponseEntity.status(HttpStatus.CREATED).body(webMapper.toDto(author));
    }

    @DeleteMapping("/{comicBookId}/authors/{authorId}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove an author role from a comicBook")
    void removeAuthor(@PathVariable UUID comicBookId,
                      @PathVariable UUID authorId,
                      @PathVariable AuthorRole role) {
        removeComicBookAuthorUseCase.removeAuthor(comicBookId, authorId, role);
    }
}
