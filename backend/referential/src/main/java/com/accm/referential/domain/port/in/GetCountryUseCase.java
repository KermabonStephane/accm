package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.Country;

public interface GetCountryUseCase {
    Country getCountryByCode(Integer countryCode);
}
