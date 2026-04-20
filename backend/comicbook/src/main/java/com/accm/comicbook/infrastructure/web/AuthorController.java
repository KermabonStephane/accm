package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.port.in.*;
import com.accm.comicbook.infrastructure.web.dto.AuthorDto;
import com.accm.comicbook.infrastructure.web.dto.ComicBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class AuthorController implements AuthorApi {

    private final ListAuthorsUseCase listAuthorsUseCase;
    private final GetAuthorUseCase getAuthorUseCase;
    private final UpdateAuthorUseCase updateAuthorUseCase;
    private final DeleteAuthorUseCase deleteAuthorUseCase;
    private final ListAuthorComicBooksUseCase listAuthorComicBooksUseCase;
    private final AuthorWebMapper authorWebMapper;
    private final ComicBookWebMapper comicBookWebMapper;

    @Override
    public ResponseEntity<List<AuthorDto>> listAuthors() {
        return ResponseEntity.ok(listAuthorsUseCase.listAuthors().stream().map(authorWebMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<AuthorDto> getAuthor(UUID id) {
        return ResponseEntity.ok(authorWebMapper.toDto(getAuthorUseCase.getAuthorById(id)));
    }

    @Override
    public ResponseEntity<AuthorDto> updateAuthor(UUID id, AuthorDto request) {
        return ResponseEntity.ok(authorWebMapper.toDto(updateAuthorUseCase.updateAuthor(id, authorWebMapper.toDomain(request))));
    }

    @Override
    public void deleteAuthor(UUID id) {
        deleteAuthorUseCase.deleteAuthor(id);
    }

    @Override
    public ResponseEntity<List<ComicBookDto>> listComicBooks(UUID id) {
        return ResponseEntity.ok(
                listAuthorComicBooksUseCase.listComicBooks(id).stream().map(comicBookWebMapper::toDto).toList());
    }
}
