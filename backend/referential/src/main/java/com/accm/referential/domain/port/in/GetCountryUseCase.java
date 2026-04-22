package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.Country;

import java.util.UUID;

public interface GetCountryUseCase {
    Country getCountryById(UUID id);
}
