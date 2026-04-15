package com.accm.people.infrastructure.persistence;

import com.accm.people.domain.model.Person;

class PersonEntityMapper {

    private PersonEntityMapper() {}

    static PersonJpaEntity toEntity(Person person) {
        PersonJpaEntity entity = new PersonJpaEntity();
        entity.setId(person.getId());
        entity.setFirstname(person.getFirstname());
        entity.setLastname(person.getLastname());
        entity.setNickname(person.getNickname());
        entity.setEmail(person.getEmail());
        entity.setStatus(person.getStatus());
        entity.setRole(person.getRole());
        entity.setPassword(person.getPassword());
        return entity;
    }

    static Person toDomain(PersonJpaEntity entity) {
        return Person.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .role(entity.getRole())
                .password(entity.getPassword())
                .build();
    }
}
