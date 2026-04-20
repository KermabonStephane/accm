package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.AuthorRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

interface ComicBookAuthorJpaRepository extends JpaRepository<ComicBookAuthorJpaEntity, UUID> {

    @Modifying
    @Query("DELETE FROM ComicBookAuthorJpaEntity a WHERE a.comicBook.id = :comicBookId AND a.author.id = :authorId AND a.role = :role")
    void deleteByComicBookIdAndAuthorIdAndRole(UUID comicBookId, UUID authorId, AuthorRole role);

    boolean existsByAuthorId(UUID authorId);
}
