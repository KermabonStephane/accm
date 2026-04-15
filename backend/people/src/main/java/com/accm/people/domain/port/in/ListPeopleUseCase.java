package com.accm.people.domain.port.in;

import com.accm.people.domain.model.Person;

import java.util.List;

public interface ListPeopleUseCase {
    List<Person> listPeople();
}
