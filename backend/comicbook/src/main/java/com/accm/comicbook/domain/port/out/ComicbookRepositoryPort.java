package com.accm.comicbook.domain.port.out;

import com.accm.comicbook.domain.model.Comicbook;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComicbookRepositoryPort {
    Comicbook save(Comicbook comicbook);
    Optional<Comicbook> findById(UUID id);
    List<Comicbook> findAll();
}
