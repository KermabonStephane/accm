package com.accm.comicbook.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

interface ComicBookJpaRepository extends JpaRepository<ComicBookJpaEntity, UUID> {

    @Query("SELECT DISTINCT c FROM ComicBookJpaEntity c JOIN c.authors a WHERE a.author.id = :authorId")
    List<ComicBookJpaEntity> findByAuthorId(UUID authorId);
}
