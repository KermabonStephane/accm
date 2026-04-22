package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.Country;

import java.util.List;

public interface ListCountriesUseCase {
    List<Country> listCountries();
}
