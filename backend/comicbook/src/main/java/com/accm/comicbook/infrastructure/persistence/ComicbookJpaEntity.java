package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.ComicbookStatus;
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
class ComicbookJpaEntity {

    @Id
    private UUID id;

    private String title;

    private String isbn;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ComicbookStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "comicbook_id")
    private List<ComicbookAuthorJpaEntity> authors = new ArrayList<>();
}
