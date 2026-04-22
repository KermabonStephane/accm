package com.accm.comicbook.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record Edition(UUID id, UUID comicBookId, UUID editorId, String isbn, LocalDate date) {
}
