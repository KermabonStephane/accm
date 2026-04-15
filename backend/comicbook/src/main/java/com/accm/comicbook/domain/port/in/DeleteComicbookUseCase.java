package com.accm.comicbook.domain.port.in;

import java.util.UUID;

public interface DeleteComicbookUseCase {
    void deleteComicbook(UUID id);
}
