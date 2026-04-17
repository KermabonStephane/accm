package com.accm.comicbook.domain.port.in;

import java.util.UUID;

public interface DeleteComicBookUseCase {
    void deleteComicBook(UUID id);
}
