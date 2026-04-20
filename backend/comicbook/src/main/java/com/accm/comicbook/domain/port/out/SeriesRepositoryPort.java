package com.accm.comicbook.domain.port.out;

import com.accm.comicbook.domain.model.Series;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeriesRepositoryPort {
    Series save(Series series);
    Optional<Series> findById(UUID id);
    List<Series> findAll();
    void delete(UUID id);
}
