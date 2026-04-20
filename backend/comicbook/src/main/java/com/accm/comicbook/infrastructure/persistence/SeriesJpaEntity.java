package com.accm.comicbook.infrastructure.persistence;

import com.accm.comicbook.domain.model.SeriesStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "series")
@Getter
@Setter
@NoArgsConstructor
class SeriesJpaEntity {

    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SeriesStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private SeriesJpaEntity parent;
}
