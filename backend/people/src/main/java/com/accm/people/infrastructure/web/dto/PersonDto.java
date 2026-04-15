package com.accm.people.infrastructure.web.dto;

import com.accm.people.domain.model.Person;
import com.accm.people.domain.model.PersonRole;
import com.accm.people.domain.model.PersonStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PersonDto(
        UUID id,
        @NotBlank String firstname,
        @NotBlank String lastname,
        String nickname,
        @NotBlank @Email String email,
        PersonStatus status,
        @NotNull PersonRole role,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password
) {
    public static PersonDto from(Person person) {
        return new PersonDto(
                person.getId(),
                person.getFirstname(),
                person.getLastname(),
                person.getNickname(),
                person.getEmail(),
                person.getStatus(),
                person.getRole(),
                null
        );
    }
}
