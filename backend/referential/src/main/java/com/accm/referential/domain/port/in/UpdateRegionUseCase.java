package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.Region;

public interface UpdateRegionUseCase {
    Region updateRegion(Integer code, Region region);
}
