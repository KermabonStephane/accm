package com.accm.comicbook.infrastructure.web;

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
@RequestMapping("/api/v1/comicBooks")
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

    @GetMapping
    @Operation(summary = "List all comicBooks")
    ResponseEntity<List<ComicBookDto>> listComicBooks() {
        return ResponseEntity.ok(
                listComicBooksUseCase.listComicBooks().stream().map(ComicBookDto::from).toList()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a comicBook by ID")
    ResponseEntity<ComicBookDto> getComicBook(@PathVariable UUID id) {
        return ResponseEntity.ok(ComicBookDto.from(getComicBookUseCase.getComicBookById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new comicBook")
    ResponseEntity<ComicBookDto> createComicBook(@RequestBody @Valid ComicBookDto request) {
        ComicBook comicBook = ComicBook.builder()
                .id(UUID.randomUUID())
                .title(request.title())
                .isbn(request.isbn())
                .date(request.date())
                .status(ComicBookStatus.ACTIVE)
                .authors(toAuthorDomain(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ComicBookDto.from(createComicBookUseCase.createComicBook(comicBook)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a comicBook")
    ResponseEntity<ComicBookDto> updateComicBook(@PathVariable UUID id,
                                                 @RequestBody @Valid ComicBookDto request) {
        ComicBook update = ComicBook.builder()
                .title(request.title())
                .isbn(request.isbn())
                .date(request.date())
                .authors(toAuthorDomain(request))
                .build();
        return ResponseEntity.ok(ComicBookDto.from(updateComicBookUseCase.updateComicBook(id, update)));
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
                        .map(AuthorDto::from)
                        .toList()
        );
    }

    @PostMapping("/{comicBookId}/authors")
    @Operation(summary = "Add an author to a comicBook")
    ResponseEntity<AuthorDto> addAuthor(@PathVariable UUID comicBookId,
                                        @RequestBody @Valid ComicBookAuthorRequest request) {
        ComicBookAuthor author = addComicBookAuthorUseCase.addAuthor(comicBookId, request.authorId(), request.role());
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthorDto.from(author));
    }

    @DeleteMapping("/{comicBookId}/authors/{authorId}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove an author role from a comicBook")
    void removeAuthor(@PathVariable UUID comicBookId,
                      @PathVariable UUID authorId,
                      @PathVariable com.accm.comicbook.domain.model.AuthorRole role) {
        removeComicBookAuthorUseCase.removeAuthor(comicBookId, authorId, role);
    }

    private static List<ComicBookAuthor> toAuthorDomain(ComicBookDto request) {
        if (request.authors() == null) {
            return List.of();
        }
        return request.authors().stream()
                .map(a -> ComicBookAuthor.builder()
                        .id(UUID.randomUUID())
                        .firstname(a.firstname())
                        .lastname(a.lastname())
                        .middleName(a.middlename())
                        .role(a.role())
                        .build())
                .toList();
    }
}
