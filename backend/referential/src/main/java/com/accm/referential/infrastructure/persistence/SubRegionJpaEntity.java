package com.accm.referential.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sub_region")
@Getter
@Setter
@NoArgsConstructor
class SubRegionJpaEntity {

    @Id
    private Integer code;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_code", nullable = false)
    private RegionJpaEntity region;
}
