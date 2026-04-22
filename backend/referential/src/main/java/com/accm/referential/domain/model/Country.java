package com.accm.referential.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Country(UUID id, String name, String alpha2, String alpha3, Integer countryCode) {
}
