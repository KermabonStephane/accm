package com.accm.comicbook.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Author(UUID id, String firstname, String lastname, String middlename) {
}
