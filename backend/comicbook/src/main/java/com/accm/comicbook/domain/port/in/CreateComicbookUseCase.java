package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Comicbook;

public interface CreateComicbookUseCase {
    Comicbook createComicbook(Comicbook comicbook);
}
