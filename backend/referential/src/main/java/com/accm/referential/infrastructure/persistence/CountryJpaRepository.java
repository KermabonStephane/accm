package com.accm.referential.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface CountryJpaRepository extends JpaRepository<CountryJpaEntity, Integer> {
}
