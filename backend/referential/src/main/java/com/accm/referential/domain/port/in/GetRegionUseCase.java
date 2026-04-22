package com.accm.referential.domain.port.in;

import com.accm.referential.domain.model.Region;

public interface GetRegionUseCase {
    Region getRegionByCode(Integer code);
}
