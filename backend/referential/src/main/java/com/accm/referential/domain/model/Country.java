package com.accm.referential.domain.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Country(Integer countryCode, String name, String alpha2, String alpha3) {
}
