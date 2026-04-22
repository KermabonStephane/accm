package com.accm.referential.infrastructure.web;

import com.accm.referential.infrastructure.web.dto.SubRegionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SubRegions", description = "Sub-region referential API")
@RequestMapping("/api/v1/sub-regions")
interface SubRegionApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new sub-region")
    ResponseEntity<SubRegionDto> createSubRegion(@RequestBody @Valid SubRegionDto request);

    @GetMapping
    @Operation(summary = "List all sub-regions")
    ResponseEntity<List<SubRegionDto>> listSubRegions();

    @GetMapping("/{code}")
    @Operation(summary = "Get a sub-region by its code")
    ResponseEntity<SubRegionDto> getSubRegion(@PathVariable Integer code);

    @PutMapping("/{code}")
    @Operation(summary = "Update a sub-region")
    ResponseEntity<SubRegionDto> updateSubRegion(@PathVariable Integer code, @RequestBody @Valid SubRegionDto request);

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a sub-region")
    void deleteSubRegion(@PathVariable Integer code);
}
