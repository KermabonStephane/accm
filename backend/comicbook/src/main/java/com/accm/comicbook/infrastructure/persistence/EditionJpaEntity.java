package com.accm.comicbook.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "edition")
@Getter
@Setter
@NoArgsConstructor
class EditionJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_book_id", nullable = false)
    private ComicBookJpaEntity comicBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editor_id")
    private EditorJpaEntity editor;

    private String isbn;

    private LocalDate date;
}
