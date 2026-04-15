package com.accm.people.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Person {

    private final UUID id;
    private String firstname;
    private String lastname;
    private String nickname;
    private String email;
    private PersonStatus status;
    private PersonRole role;
    private String password;

    public void update(String firstname, String lastname, String nickname, String email, PersonRole role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    public void markAsDeleted() {
        this.status = PersonStatus.DELETED;
    }
}
