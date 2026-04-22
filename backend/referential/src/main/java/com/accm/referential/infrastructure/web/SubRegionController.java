package com.accm.referential.infrastructure.web;

import com.accm.referential.domain.port.in.*;
import com.accm.referential.infrastructure.web.dto.SubRegionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class SubRegionController implements SubRegionApi {

    private final CreateSubRegionUseCase createSubRegionUseCase;
    private final GetSubRegionUseCase getSubRegionUseCase;
    private final ListSubRegionsUseCase listSubRegionsUseCase;
    private final UpdateSubRegionUseCase updateSubRegionUseCase;
    private final DeleteSubRegionUseCase deleteSubRegionUseCase;
    private final SubRegionWebMapper subRegionWebMapper;

    @Override
    public ResponseEntity<SubRegionDto> createSubRegion(SubRegionDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subRegionWebMapper.toDto(createSubRegionUseCase.createSubRegion(subRegionWebMapper.toDomain(request))));
    }

    @Override
    public ResponseEntity<List<SubRegionDto>> listSubRegions() {
        return ResponseEntity.ok(listSubRegionsUseCase.listSubRegions().stream().map(subRegionWebMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<SubRegionDto> getSubRegion(Integer code) {
        return ResponseEntity.ok(subRegionWebMapper.toDto(getSubRegionUseCase.getSubRegionByCode(code)));
    }

    @Override
    public ResponseEntity<SubRegionDto> updateSubRegion(Integer code, SubRegionDto request) {
        return ResponseEntity.ok(subRegionWebMapper.toDto(
                updateSubRegionUseCase.updateSubRegion(code, subRegionWebMapper.toDomain(request))));
    }

    @Override
    public void deleteSubRegion(Integer code) {
        deleteSubRegionUseCase.deleteSubRegion(code);
    }
}
