package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.AuthorRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "comicbook_author")
@Getter
@Setter
@NoArgsConstructor
class ComicbookAuthorJpaEntity {

    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AuthorRole role;
}
