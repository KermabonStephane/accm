package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Series;

import java.util.List;

public interface ListSeriesUseCase {
    List<Series> listSeries();
}
