package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.ComicBookStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "comicbook")
@Getter
@Setter
@NoArgsConstructor
class ComicBookJpaEntity {

    @Id
    private UUID id;

    private String title;

    private String isbn;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ComicBookStatus status;

    private Integer issueNumber;

    private Integer volumeNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private SeriesJpaEntity series;

    @OneToMany(mappedBy = "comicBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ComicBookAuthorJpaEntity> authors = new ArrayList<>();
}
