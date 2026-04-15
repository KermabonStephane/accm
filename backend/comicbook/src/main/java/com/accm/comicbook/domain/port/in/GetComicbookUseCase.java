package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Comicbook;

import java.util.UUID;

public interface GetComicbookUseCase {
    Comicbook getComicbookById(UUID id);
}
