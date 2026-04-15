package com.accm.people.domain.port.in;

import com.accm.people.application.command.CreatePersonCommand;
import com.accm.people.domain.model.Person;

public interface CreatePersonUseCase {
    Person createPerson(CreatePersonCommand command);
}
