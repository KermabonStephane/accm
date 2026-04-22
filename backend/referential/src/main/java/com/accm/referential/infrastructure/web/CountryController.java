package com.accm.referential.infrastructure.web;

import com.accm.referential.domain.port.in.*;
import com.accm.referential.infrastructure.web.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class CountryController implements CountryApi {

    private final CreateCountryUseCase createCountryUseCase;
    private final GetCountryUseCase getCountryUseCase;
    private final ListCountriesUseCase listCountriesUseCase;
    private final UpdateCountryUseCase updateCountryUseCase;
    private final DeleteCountryUseCase deleteCountryUseCase;
    private final CountryWebMapper countryWebMapper;

    @Override
    public ResponseEntity<CountryDto> createCountry(CountryDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(countryWebMapper.toDto(createCountryUseCase.createCountry(countryWebMapper.toDomain(request))));
    }

    @Override
    public ResponseEntity<List<CountryDto>> listCountries() {
        return ResponseEntity.ok(listCountriesUseCase.listCountries().stream().map(countryWebMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<CountryDto> getCountry(UUID id) {
        return ResponseEntity.ok(countryWebMapper.toDto(getCountryUseCase.getCountryById(id)));
    }

    @Override
    public ResponseEntity<CountryDto> updateCountry(UUID id, CountryDto request) {
        return ResponseEntity.ok(countryWebMapper.toDto(
                updateCountryUseCase.updateCountry(id, countryWebMapper.toDomain(request))));
    }

    @Override
    public void deleteCountry(UUID id) {
        deleteCountryUseCase.deleteCountry(id);
    }
}
