package com.accm.people.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PersonJpaRepository extends JpaRepository<PersonJpaEntity, UUID> {
    boolean existsByEmail(String email);
}
