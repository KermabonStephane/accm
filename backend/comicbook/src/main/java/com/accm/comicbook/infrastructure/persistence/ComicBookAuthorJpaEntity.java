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
class ComicBookAuthorJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comicbook_id", nullable = false)
    private ComicBookJpaEntity comicBook;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "author_id")
    private AuthorJpaEntity author;

    @Enumerated(EnumType.STRING)
    private AuthorRole role;
}
