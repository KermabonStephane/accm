package com.accm.comicbook.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface SeriesJpaRepository extends JpaRepository<SeriesJpaEntity, UUID> {
}
