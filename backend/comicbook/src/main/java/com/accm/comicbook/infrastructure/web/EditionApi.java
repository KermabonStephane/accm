package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.infrastructure.web.dto.EditionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Editions", description = "Edition management API")
@RequestMapping("/api/v1/editions")
interface EditionApi {

    @GetMapping("/{id}")
    @Operation(summary = "Get an edition by ID")
    ResponseEntity<EditionDto> getEdition(@PathVariable UUID id);

    @PutMapping("/{id}")
    @Operation(summary = "Update an edition")
    ResponseEntity<EditionDto> updateEdition(@PathVariable UUID id, @RequestBody @Valid EditionDto request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an edition")
    void deleteEdition(@PathVariable UUID id);
}
