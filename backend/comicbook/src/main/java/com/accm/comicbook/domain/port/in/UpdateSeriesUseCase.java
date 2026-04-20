package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Series;

import java.util.UUID;

public interface UpdateSeriesUseCase {
    Series updateSeries(UUID id, Series update);
}
