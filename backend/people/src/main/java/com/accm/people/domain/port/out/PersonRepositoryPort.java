package com.accm.people.domain.port.out;

import com.accm.people.domain.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepositoryPort {
    Person save(Person person);
    Optional<Person> findById(UUID id);
    List<Person> findAll();
    boolean existsByEmail(String email);
}
