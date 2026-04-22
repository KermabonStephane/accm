package com.accm.referential.infrastructure.web;

import com.accm.referential.domain.port.in.*;
import com.accm.referential.infrastructure.web.dto.RegionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class RegionController implements RegionApi {

    private final CreateRegionUseCase createRegionUseCase;
    private final GetRegionUseCase getRegionUseCase;
    private final ListRegionsUseCase listRegionsUseCase;
    private final UpdateRegionUseCase updateRegionUseCase;
    private final DeleteRegionUseCase deleteRegionUseCase;
    private final RegionWebMapper regionWebMapper;

    @Override
    public ResponseEntity<RegionDto> createRegion(RegionDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(regionWebMapper.toDto(createRegionUseCase.createRegion(regionWebMapper.toDomain(request))));
    }

    @Override
    public ResponseEntity<List<RegionDto>> listRegions() {
        return ResponseEntity.ok(listRegionsUseCase.listRegions().stream().map(regionWebMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<RegionDto> getRegion(Integer code) {
        return ResponseEntity.ok(regionWebMapper.toDto(getRegionUseCase.getRegionByCode(code)));
    }

    @Override
    public ResponseEntity<RegionDto> updateRegion(Integer code, RegionDto request) {
        return ResponseEntity.ok(regionWebMapper.toDto(
                updateRegionUseCase.updateRegion(code, regionWebMapper.toDomain(request))));
    }

    @Override
    public void deleteRegion(Integer code) {
        deleteRegionUseCase.deleteRegion(code);
    }
}
