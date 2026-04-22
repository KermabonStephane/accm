package com.accm.referential.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CountryJpaRepository extends JpaRepository<CountryJpaEntity, UUID> {
}
