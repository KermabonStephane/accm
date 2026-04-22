package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.EditorStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "editor")
@Getter
@Setter
@NoArgsConstructor
class EditorJpaEntity {

    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private EditorStatus status;
}
