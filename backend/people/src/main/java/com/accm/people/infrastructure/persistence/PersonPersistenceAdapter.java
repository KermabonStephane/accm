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
        return PersonEntityMapper.toDomain(jpaRepository.save(PersonEntityMapper.toEntity(person)));
    }

    @Override
    public Optional<Person> findById(UUID id) {
        return jpaRepository.findById(id).map(PersonEntityMapper::toDomain);
    }

    @Override
    public List<Person> findAll() {
        return jpaRepository.findAll().stream().map(PersonEntityMapper::toDomain).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
