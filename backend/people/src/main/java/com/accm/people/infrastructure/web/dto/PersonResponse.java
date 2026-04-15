package com.accm.people.infrastructure.web.dto;

import com.accm.people.domain.model.Person;
import com.accm.people.domain.model.PersonRole;
import com.accm.people.domain.model.PersonStatus;

import java.util.UUID;

public record PersonResponse(
        UUID id,
        String firstname,
        String lastname,
        String nickname,
        String email,
        PersonStatus status,
        PersonRole role
) {
    public static PersonResponse from(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getFirstname(),
                person.getLastname(),
                person.getNickname(),
                person.getEmail(),
                person.getStatus(),
                person.getRole()
        );
    }
}
