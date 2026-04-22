package com.accm.referential.domain.port.out;

import com.accm.referential.domain.model.Region;

import java.util.List;
import java.util.Optional;

public interface RegionRepositoryPort {
    Region save(Region region);
    Optional<Region> findByCode(Integer code);
    List<Region> findAll();
    void delete(Integer code);
}
