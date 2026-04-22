package com.accm.comicbook.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface EditionJpaRepository extends JpaRepository<EditionJpaEntity, UUID> {
    List<EditionJpaEntity> findByComicBook_Id(UUID comicBookId);
}
