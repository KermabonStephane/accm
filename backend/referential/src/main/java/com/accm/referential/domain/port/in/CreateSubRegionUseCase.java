package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.SubRegion;

public interface CreateSubRegionUseCase {
    SubRegion createSubRegion(SubRegion subRegion);
}
