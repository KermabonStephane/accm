package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.EditorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface EditorJpaRepository extends JpaRepository<EditorJpaEntity, UUID> {
    List<EditorJpaEntity> findByStatus(EditorStatus status);
}
