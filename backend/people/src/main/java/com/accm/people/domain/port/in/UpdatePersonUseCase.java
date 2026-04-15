package com.accm.people.domain.port.in;

import com.accm.people.domain.model.Person;

import java.util.UUID;

public interface UpdatePersonUseCase {
    Person updatePerson(UUID id, Person person);
}
