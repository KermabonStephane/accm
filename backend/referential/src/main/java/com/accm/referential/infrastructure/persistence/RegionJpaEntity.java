package com.accm.referential.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "region")
@Getter
@Setter
@NoArgsConstructor
class RegionJpaEntity {

    @Id
    private Integer code;

    private String name;
}
