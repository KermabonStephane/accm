package com.accm.comicbook.domain.port.out;

import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicBook;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComicBookRepositoryPort {
    ComicBook save(ComicBook comicBook);
    Optional<ComicBook> findById(UUID id);
    List<ComicBook> findAll();
    void linkAuthor(UUID comicBookId, UUID authorId, AuthorRole role);
    void unlinkAuthor(UUID comicBookId, UUID authorId, AuthorRole role);
    List<ComicBook> findByAuthorId(UUID authorId);
    List<ComicBook> findBySeriesId(UUID seriesId);
    boolean existsBySeriesIssueVolume(UUID seriesId, Integer issueNumber, Integer volumeNumber, UUID excludeId);
}
