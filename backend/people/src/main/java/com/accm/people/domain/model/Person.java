package com.accm.people.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Person {

    private final UUID id;
    private final String firstname;
    private final String lastname;
    private final String nickname;
    private final String email;
    private final PersonStatus status;
    private final PersonRole role;
    private final String password;
}
