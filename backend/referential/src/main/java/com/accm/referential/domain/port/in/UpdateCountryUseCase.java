package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.Country;

import java.util.UUID;

public interface UpdateCountryUseCase {
    Country updateCountry(UUID id, Country country);
}
