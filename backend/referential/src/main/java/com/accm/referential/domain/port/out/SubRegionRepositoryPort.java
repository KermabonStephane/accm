package com.accm.referential.domain.port.out;

import com.accm.referential.domain.model.SubRegion;

import java.util.List;
import java.util.Optional;

public interface SubRegionRepositoryPort {
    SubRegion save(SubRegion subRegion);
    Optional<SubRegion> findByCode(Integer code);
    List<SubRegion> findAll();
    void delete(Integer code);
}
