package com.accm.referential.domain.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record SubRegion(Integer code, String name, Integer regionCode) {
}
