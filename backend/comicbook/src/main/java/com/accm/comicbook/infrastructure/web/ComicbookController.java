package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.Comicbook;
import com.accm.comicbook.domain.model.ComicbookAuthor;
import com.accm.comicbook.domain.model.ComicbookStatus;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.infrastructure.web.dto.ComicbookDto;
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
@Tag(name = "Comicbooks", description = "Comicbook collection management API")
class ComicbookController {

    private final CreateComicbookUseCase createComicbookUseCase;
    private final GetComicbookUseCase getComicbookUseCase;
    private final ListComicbooksUseCase listComicbooksUseCase;
    private final UpdateComicbookUseCase updateComicbookUseCase;
    private final DeleteComicbookUseCase deleteComicbookUseCase;

    @GetMapping
    @Operation(summary = "List all comicbooks")
    ResponseEntity<List<ComicbookDto>> listComicbooks() {
        return ResponseEntity.ok(
                listComicbooksUseCase.listComicbooks().stream().map(ComicbookDto::from).toList()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a comicbook by ID")
    ResponseEntity<ComicbookDto> getComicbook(@PathVariable UUID id) {
        return ResponseEntity.ok(ComicbookDto.from(getComicbookUseCase.getComicbookById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new comicbook")
    ResponseEntity<ComicbookDto> createComicbook(@RequestBody @Valid ComicbookDto request) {
        Comicbook comicbook = Comicbook.builder()
                .id(UUID.randomUUID())
                .title(request.title())
                .isbn(request.isbn())
                .date(request.date())
                .status(ComicbookStatus.ACTIVE)
                .authors(toAuthorDomain(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ComicbookDto.from(createComicbookUseCase.createComicbook(comicbook)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a comicbook")
    ResponseEntity<ComicbookDto> updateComicbook(@PathVariable UUID id,
                                                 @RequestBody @Valid ComicbookDto request) {
        Comicbook update = Comicbook.builder()
                .title(request.title())
                .isbn(request.isbn())
                .date(request.date())
                .authors(toAuthorDomain(request))
                .build();
        return ResponseEntity.ok(ComicbookDto.from(updateComicbookUseCase.updateComicbook(id, update)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft-delete a comicbook (marks status as DELETED)")
    void deleteComicbook(@PathVariable UUID id) {
        deleteComicbookUseCase.deleteComicbook(id);
    }

    private static List<ComicbookAuthor> toAuthorDomain(ComicbookDto request) {
        if (request.authors() == null) {
            return List.of();
        }
        return request.authors().stream()
                .map(a -> ComicbookAuthor.builder()
                        .id(UUID.randomUUID())
                        .name(a.name())
                        .role(a.role())
                        .build())
                .toList();
    }
}
