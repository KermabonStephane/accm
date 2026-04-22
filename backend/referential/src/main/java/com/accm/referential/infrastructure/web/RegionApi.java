package com.accm.referential.infrastructure.web;

import com.accm.referential.infrastructure.web.dto.RegionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Regions", description = "Region referential API")
@RequestMapping("/api/v1/regions")
interface RegionApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new region")
    ResponseEntity<RegionDto> createRegion(@RequestBody @Valid RegionDto request);

    @GetMapping
    @Operation(summary = "List all regions")
    ResponseEntity<List<RegionDto>> listRegions();

    @GetMapping("/{code}")
    @Operation(summary = "Get a region by its code")
    ResponseEntity<RegionDto> getRegion(@PathVariable Integer code);

    @PutMapping("/{code}")
    @Operation(summary = "Update a region")
    ResponseEntity<RegionDto> updateRegion(@PathVariable Integer code, @RequestBody @Valid RegionDto request);

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a region")
    void deleteRegion(@PathVariable Integer code);
}
