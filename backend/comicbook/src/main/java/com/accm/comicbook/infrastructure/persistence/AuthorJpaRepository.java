package com.accm.comicbook.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AuthorJpaRepository extends JpaRepository<AuthorJpaEntity, UUID> {
}
