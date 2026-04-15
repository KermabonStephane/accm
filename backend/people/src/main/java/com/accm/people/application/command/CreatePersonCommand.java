package com.accm.people.application.command;

import com.accm.people.domain.model.PersonRole;

public record CreatePersonCommand(
        String firstname,
        String lastname,
        String nickname,
        String email,
        PersonRole role,
        String password
) {}
