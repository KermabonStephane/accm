package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.Country;

public interface UpdateCountryUseCase {
    Country updateCountry(Integer countryCode, Country country);
}
