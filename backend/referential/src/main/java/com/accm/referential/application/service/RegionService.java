package com.accm.referential.application.service;

import com.accm.referential.domain.model.Region;
import com.accm.referential.domain.port.in.*;
import com.accm.referential.domain.port.out.RegionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService implements CreateRegionUseCase, GetRegionUseCase, ListRegionsUseCase,
        UpdateRegionUseCase, DeleteRegionUseCase {

    private final RegionRepositoryPort regionRepository;

    @Override
    public Region createRegion(Region region) {
        return regionRepository.save(region);
    }

    @Override
    @Transactional(readOnly = true)
    public Region getRegionByCode(Integer code) {
        return regionRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("Region not found: " + code));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> listRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Region updateRegion(Integer code, Region update) {
        Region existing = getRegionByCode(code);
        return regionRepository.save(existing.toBuilder().name(update.name()).build());
    }

    @Override
    public void deleteRegion(Integer code) {
        getRegionByCode(code);
        regionRepository.delete(code);
    }
}
