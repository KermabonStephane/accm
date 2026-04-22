package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.infrastructure.web.dto.EditorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Editors", description = "Editor management API")
@RequestMapping("/api/v1/editors")
interface EditorApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new editor")
    ResponseEntity<EditorDto> createEditor(@RequestBody @Valid EditorDto request);

    @GetMapping
    @Operation(summary = "List all active editors")
    ResponseEntity<List<EditorDto>> listEditors();

    @GetMapping("/{id}")
    @Operation(summary = "Get an editor by ID")
    ResponseEntity<EditorDto> getEditor(@PathVariable UUID id);

    @PutMapping("/{id}")
    @Operation(summary = "Update an editor")
    ResponseEntity<EditorDto> updateEditor(@PathVariable UUID id, @RequestBody @Valid EditorDto request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Logically delete an editor")
    void deleteEditor(@PathVariable UUID id);
}
