package com.accm.referential.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
class CountryJpaEntity {

    @Id
    private Integer countryCode;

    private String name;

    private String alpha2;

    private String alpha3;
}
