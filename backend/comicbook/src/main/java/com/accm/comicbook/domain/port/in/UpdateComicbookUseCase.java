package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Comicbook;

import java.util.UUID;

public interface UpdateComicbookUseCase {
    Comicbook updateComicbook(UUID id, Comicbook comicbook);
}
