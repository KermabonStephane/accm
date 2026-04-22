package com.accm.referential.application.service;

import com.accm.referential.domain.model.Country;
import com.accm.referential.domain.port.in.*;
import com.accm.referential.domain.port.out.CountryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryService implements CreateCountryUseCase, GetCountryUseCase, ListCountriesUseCase,
        UpdateCountryUseCase, DeleteCountryUseCase {

    private final CountryRepositoryPort countryRepository;

    @Override
    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    @Transactional(readOnly = true)
    public Country getCountryByCode(Integer countryCode) {
        return countryRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new NoSuchElementException("Country not found: " + countryCode));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> listCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country updateCountry(Integer countryCode, Country update) {
        Country existing = getCountryByCode(countryCode);
        return countryRepository.save(existing.toBuilder()
                .name(update.name())
                .alpha2(update.alpha2())
                .alpha3(update.alpha3())
                .regionCode(update.regionCode())
                .subRegionCode(update.subRegionCode())
                .build());
    }

    @Override
    public void deleteCountry(Integer countryCode) {
        getCountryByCode(countryCode);
        countryRepository.delete(countryCode);
    }
}
