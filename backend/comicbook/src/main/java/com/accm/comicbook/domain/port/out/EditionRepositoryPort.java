package com.accm.comicbook.domain.port.out;

import com.accm.comicbook.domain.model.Edition;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EditionRepositoryPort {
    Edition save(Edition edition);
    Optional<Edition> findById(UUID id);
    List<Edition> findByComicBookId(UUID comicBookId);
    void delete(UUID id);
}
