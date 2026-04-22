package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.Region;

public interface CreateRegionUseCase {
    Region createRegion(Region region);
}
