package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Edition;

import java.util.List;
import java.util.UUID;

public interface ListComicBookEditionsUseCase {
    List<Edition> listEditions(UUID comicBookId);
}
