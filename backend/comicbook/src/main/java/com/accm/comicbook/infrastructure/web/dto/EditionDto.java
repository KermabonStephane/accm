package com.accm.comicbook.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record EditionDto(UUID id, String isbn, LocalDate date, UUID editorId) {
}
