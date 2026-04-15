package com.accm.people.application.service;

import com.accm.people.application.command.CreatePersonCommand;
import com.accm.people.application.command.UpdatePersonCommand;
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
    public Person createPerson(CreatePersonCommand command) {
        Person person = Person.builder()
                .id(UUID.randomUUID())
                .firstname(command.firstname())
                .lastname(command.lastname())
                .nickname(command.nickname())
                .email(command.email())
                .status(PersonStatus.CREATED)
                .role(command.role())
                .password(command.password())
                .build();
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
    public Person updatePerson(UUID id, UpdatePersonCommand command) {
        Person person = getPersonById(id);
        person.update(command.firstname(), command.lastname(), command.nickname(),
                command.email(), command.role());
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(UUID id) {
        Person person = getPersonById(id);
        person.markAsDeleted();
        personRepository.save(person);
    }
}
