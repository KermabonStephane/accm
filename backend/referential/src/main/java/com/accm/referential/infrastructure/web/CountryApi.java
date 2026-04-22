package com.accm.referential.infrastructure.web;

import com.accm.referential.infrastructure.web.dto.CountryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    @Operation(summary = "Get a country by ID")
    ResponseEntity<CountryDto> getCountry(@PathVariable UUID id);

    @PutMapping("/{id}")
    @Operation(summary = "Update a country")
    ResponseEntity<CountryDto> updateCountry(@PathVariable UUID id, @RequestBody @Valid CountryDto request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a country")
    void deleteCountry(@PathVariable UUID id);
}
