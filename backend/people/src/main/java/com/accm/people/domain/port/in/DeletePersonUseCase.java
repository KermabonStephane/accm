package com.accm.people.domain.port.in;

import java.util.UUID;

public interface DeletePersonUseCase {
    void deletePerson(UUID id);
}
