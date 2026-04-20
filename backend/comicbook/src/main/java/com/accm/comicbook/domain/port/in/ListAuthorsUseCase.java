package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Author;

import java.util.List;

public interface ListAuthorsUseCase {
    List<Author> listAuthors();
}
