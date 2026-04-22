package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Edition;

public interface CreateEditionUseCase {
    Edition createEdition(Edition edition);
}
