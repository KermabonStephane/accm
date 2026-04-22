package com.accm.referential.infrastructure.web;

import com.accm.referential.infrastructure.web.dto.CountryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Countries", description = "Country referential API")
@RequestMapping("/api/v1/countries")
interface CountryApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new country")
    ResponseEntity<CountryDto> createCountry(@RequestBody @Valid CountryDto request);

    @GetMapping
    @Operation(summary = "List all countries")
    ResponseEntity<List<CountryDto>> listCountries();

    @GetMapping("/{countryCode}")
    @Operation(summary = "Get a country by its numeric code")
    ResponseEntity<CountryDto> getCountry(@PathVariable Integer countryCode);

    @PutMapping("/{countryCode}")
    @Operation(summary = "Update a country")
    ResponseEntity<CountryDto> updateCountry(@PathVariable Integer countryCode, @RequestBody @Valid CountryDto request);

    @DeleteMapping("/{countryCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a country")
    void deleteCountry(@PathVariable Integer countryCode);
}
