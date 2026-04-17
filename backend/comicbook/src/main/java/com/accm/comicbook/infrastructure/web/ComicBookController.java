package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;
import com.accm.comicbook.domain.model.ComicBookStatus;
import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto.AuthorDto;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto.ComicBookAuthorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class ComicBookController implements ComicBookApi {

    private final CreateComicBookUseCase createComicBookUseCase;
    private final GetComicBookUseCase getComicBookUseCase;
    private final ListComicBooksUseCase listComicBooksUseCase;
    private final UpdateComicBookUseCase updateComicBookUseCase;
    private final DeleteComicBookUseCase deleteComicBookUseCase;
    private final ListComicBookAuthorsUseCase listComicBookAuthorsUseCase;
    private final AddComicBookAuthorUseCase addComicBookAuthorUseCase;
    private final RemoveComicBookAuthorUseCase removeComicBookAuthorUseCase;
    private final ComicBookWebMapper webMapper;

    @Override
    public ResponseEntity<List<ComicBookDto>> listComicBooks() {
        return ResponseEntity.ok(
                listComicBooksUseCase.listComicBooks().stream().map(webMapper::toDto).toList()
        );
    }

    @Override
    public ResponseEntity<ComicBookDto> getComicBook(UUID id) {
        return ResponseEntity.ok(webMapper.toDto(getComicBookUseCase.getComicBookById(id)));
    }

    @Override
    public ResponseEntity<ComicBookDto> createComicBook(ComicBookDto request) {
        ComicBook comicBook = webMapper.toDomain(request).toBuilder()
                .id(UUID.randomUUID())
                .status(ComicBookStatus.ACTIVE)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(webMapper.toDto(createComicBookUseCase.createComicBook(comicBook)));
    }

    @Override
    public ResponseEntity<ComicBookDto> updateComicBook(UUID id, ComicBookDto request) {
        return ResponseEntity.ok(
                webMapper.toDto(updateComicBookUseCase.updateComicBook(id, webMapper.toDomain(request))));
    }

    @Override
    public void deleteComicBook(UUID id) {
        deleteComicBookUseCase.deleteComicBook(id);
    }

    @Override
    public ResponseEntity<List<AuthorDto>> listAuthors(UUID comicBookId) {
        return ResponseEntity.ok(
                listComicBookAuthorsUseCase.listAuthors(comicBookId).stream()
                        .map(webMapper::toDto)
                        .toList()
        );
    }

    @Override
    public ResponseEntity<AuthorDto> addAuthor(UUID comicBookId, ComicBookAuthorRequest request) {
        ComicBookAuthor author = addComicBookAuthorUseCase.addAuthor(comicBookId, request.authorId(), request.role());
        return ResponseEntity.status(HttpStatus.CREATED).body(webMapper.toDto(author));
    }

    @Override
    public void removeAuthor(UUID comicBookId, UUID authorId, AuthorRole role) {
        removeComicBookAuthorUseCase.removeAuthor(comicBookId, authorId, role);
    }
}
