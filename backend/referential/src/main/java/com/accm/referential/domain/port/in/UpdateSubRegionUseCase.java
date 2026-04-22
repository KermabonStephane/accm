package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.SubRegion;

public interface UpdateSubRegionUseCase {
    SubRegion updateSubRegion(Integer code, SubRegion subRegion);
}
