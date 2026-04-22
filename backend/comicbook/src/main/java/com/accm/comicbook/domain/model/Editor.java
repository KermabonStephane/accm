package com.accm.comicbook.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Editor(UUID id, String name, EditorStatus status) {
}
