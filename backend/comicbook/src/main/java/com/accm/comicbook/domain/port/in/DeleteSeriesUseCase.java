package com.accm.comicbook.domain.port.in;

import java.util.UUID;

public interface DeleteSeriesUseCase {
    void deleteSeries(UUID id);
}
