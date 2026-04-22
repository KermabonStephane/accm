package com.accm.referential.domain.port.out;

import com.accm.referential.domain.model.Country;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepositoryPort {
    Country save(Country country);
    Optional<Country> findById(UUID id);
    List<Country> findAll();
    void delete(UUID id);
}
