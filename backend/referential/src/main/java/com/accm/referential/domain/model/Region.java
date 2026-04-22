package com.accm.referential.domain.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Region(Integer code, String name) {
}
