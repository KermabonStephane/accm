package com.accm.people.infrastructure.persistence;

import com.accm.people.domain.model.Person;
import com.accm.people.domain.port.out.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PersonPersistenceAdapter implements PersonRepositoryPort {

    private final PersonJpaRepository jpaRepository;

    @Override
    public Person save(Person person) {
        return toDomain(jpaRepository.save(toEntity(person)));
    }

    @Override
    public Optional<Person> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Person> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    private PersonJpaEntity toEntity(Person person) {
        PersonJpaEntity entity = new PersonJpaEntity();
        entity.setId(person.getId());
        entity.setFirstname(person.getFirstname());
        entity.setLastname(person.getLastname());
        entity.setNickname(person.getNickname());
        entity.setEmail(person.getEmail());
        entity.setStatus(person.getStatus());
        entity.setRole(person.getRole());
        entity.setPassword(person.getPassword());
        return entity;
    }

    private Person toDomain(PersonJpaEntity entity) {
        return Person.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .role(entity.getRole())
                .password(entity.getPassword())
                .build();
    }
}
