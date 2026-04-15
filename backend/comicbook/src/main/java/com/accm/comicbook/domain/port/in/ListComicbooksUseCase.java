package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Comicbook;

import java.util.List;

public interface ListComicbooksUseCase {
    List<Comicbook> listComicbooks();
}
