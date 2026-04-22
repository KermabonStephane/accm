package com.accm.referential.application.service;

import com.accm.referential.domain.model.Country;
import com.accm.referential.domain.port.in.*;
import com.accm.referential.domain.port.out.CountryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryService implements CreateCountryUseCase, GetCountryUseCase, ListCountriesUseCase,
        UpdateCountryUseCase, DeleteCountryUseCase {

    private final CountryRepositoryPort countryRepository;

    @Override
    public Country createCountry(Country country) {
        return countryRepository.save(country.toBuilder().id(UUID.randomUUID()).build());
    }

    @Override
    @Transactional(readOnly = true)
    public Country getCountryById(UUID id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Country not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> listCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country updateCountry(UUID id, Country update) {
        Country existing = getCountryById(id);
        return countryRepository.save(existing.toBuilder()
                .name(update.name())
                .alpha2(update.alpha2())
                .alpha3(update.alpha3())
                .countryCode(update.countryCode())
                .build());
    }

    @Override
    public void deleteCountry(UUID id) {
        getCountryById(id);
        countryRepository.delete(id);
    }
}
