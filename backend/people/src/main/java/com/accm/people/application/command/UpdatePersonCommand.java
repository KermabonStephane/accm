package com.accm.people.application.command;

import com.accm.people.domain.model.PersonRole;

public record UpdatePersonCommand(
        String firstname,
        String lastname,
        String nickname,
        String email,
        PersonRole role
) {}
