package com.accm.people.infrastructure.web.dto;

import com.accm.people.domain.model.PersonRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePersonRequest(
        @NotBlank String firstname,
        @NotBlank String lastname,
        String nickname,
        @NotBlank @Email String email,
        @NotNull PersonRole role
) {}
