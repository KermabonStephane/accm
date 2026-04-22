package com.accm.referential.application.service;

import com.accm.referential.domain.model.SubRegion;
import com.accm.referential.domain.port.in.*;
import com.accm.referential.domain.port.out.RegionRepositoryPort;
import com.accm.referential.domain.port.out.SubRegionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class SubRegionService implements CreateSubRegionUseCase, GetSubRegionUseCase, ListSubRegionsUseCase,
        UpdateSubRegionUseCase, DeleteSubRegionUseCase {

    private final SubRegionRepositoryPort subRegionRepository;
    private final RegionRepositoryPort regionRepository;

    @Override
    public SubRegion createSubRegion(SubRegion subRegion) {
        regionRepository.findByCode(subRegion.regionCode())
                .orElseThrow(() -> new NoSuchElementException("Region not found: " + subRegion.regionCode()));
        return subRegionRepository.save(subRegion);
    }

    @Override
    @Transactional(readOnly = true)
    public SubRegion getSubRegionByCode(Integer code) {
        return subRegionRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("SubRegion not found: " + code));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubRegion> listSubRegions() {
        return subRegionRepository.findAll();
    }

    @Override
    public SubRegion updateSubRegion(Integer code, SubRegion update) {
        SubRegion existing = getSubRegionByCode(code);
        return subRegionRepository.save(existing.toBuilder().name(update.name()).build());
    }

    @Override
    public void deleteSubRegion(Integer code) {
        getSubRegionByCode(code);
        subRegionRepository.delete(code);
    }
}
