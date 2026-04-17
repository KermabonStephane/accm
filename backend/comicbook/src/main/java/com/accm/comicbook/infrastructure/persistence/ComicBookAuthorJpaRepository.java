package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.AuthorRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface ComicBookAuthorJpaRepository extends JpaRepository<ComicBookAuthorJpaEntity, UUID> {

    Optional<ComicBookAuthorJpaEntity> findByComicBookIdAndAuthorIdAndRole(
            UUID comicBookId, UUID authorId, AuthorRole role);
}
