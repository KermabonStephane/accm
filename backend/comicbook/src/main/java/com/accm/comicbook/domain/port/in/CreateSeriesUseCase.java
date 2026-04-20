package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Series;

public interface CreateSeriesUseCase {
    Series createSeries(Series series);
}
