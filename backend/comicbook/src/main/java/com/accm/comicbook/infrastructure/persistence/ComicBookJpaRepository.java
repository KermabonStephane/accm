package com.accm.comicbook.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface ComicBookJpaRepository extends JpaRepository<ComicBookJpaEntity, UUID> {
}
