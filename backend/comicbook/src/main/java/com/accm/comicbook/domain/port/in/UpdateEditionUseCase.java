package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Edition;

import java.util.UUID;

public interface UpdateEditionUseCase {
    Edition updateEdition(UUID id, Edition update);
}
