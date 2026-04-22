package com.accm.referential.domain.port.in;

import java.util.UUID;

public interface DeleteCountryUseCase {
    void deleteCountry(UUID id);
}
