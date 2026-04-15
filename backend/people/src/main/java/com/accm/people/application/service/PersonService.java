package com.accm.people.application.service;

import com.accm.people.domain.model.Person;
import com.accm.people.domain.model.PersonStatus;
import com.accm.people.domain.port.in.*;
import com.accm.people.domain.port.out.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService implements CreatePersonUseCase, UpdatePersonUseCase,
        GetPersonUseCase, ListPeopleUseCase, DeletePersonUseCase {

    private final PersonRepositoryPort personRepository;

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPersonById(UUID id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Person not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> listPeople() {
        return personRepository.findAll();
    }

    @Override
    public Person updatePerson(UUID id, Person update) {
        Person existing = getPersonById(id);
        return personRepository.save(existing.toBuilder()
                .firstname(update.getFirstname())
                .lastname(update.getLastname())
                .nickname(update.getNickname())
                .email(update.getEmail())
                .role(update.getRole())
                .build());
    }

    @Override
    public void deletePerson(UUID id) {
        Person existing = getPersonById(id);
        personRepository.save(existing.toBuilder()
                .status(PersonStatus.DELETED)
                .build());
    }
}
