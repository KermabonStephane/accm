package com.accm.comicbook.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Series(UUID id, String name, SeriesStatus status, UUID parentId) {
}
