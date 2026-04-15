package com.accm.people.infrastructure.web;

import com.accm.people.application.command.CreatePersonCommand;
import com.accm.people.application.command.UpdatePersonCommand;
import com.accm.people.domain.port.in.*;
import com.accm.people.infrastructure.web.dto.PersonDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/people")
@RequiredArgsConstructor
@Tag(name = "People", description = "User management API")
public class PersonController {

    private final CreatePersonUseCase createPersonUseCase;
    private final UpdatePersonUseCase updatePersonUseCase;
    private final GetPersonUseCase getPersonUseCase;
    private final ListPeopleUseCase listPeopleUseCase;
    private final DeletePersonUseCase deletePersonUseCase;

    @GetMapping
    @Operation(summary = "List all people")
    public ResponseEntity<List<PersonDto>> listPeople() {
        return ResponseEntity.ok(
                listPeopleUseCase.listPeople().stream().map(PersonDto::from).toList()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a person by ID")
    public ResponseEntity<PersonDto> getPerson(@PathVariable UUID id) {
        return ResponseEntity.ok(PersonDto.from(getPersonUseCase.getPersonById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new person")
    public ResponseEntity<PersonDto> createPerson(@RequestBody @Valid PersonDto request) {
        CreatePersonCommand command = new CreatePersonCommand(
                request.firstname(), request.lastname(), request.nickname(),
                request.email(), request.role(), request.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PersonDto.from(createPersonUseCase.createPerson(command)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a person")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable UUID id,
                                                  @RequestBody @Valid PersonDto request) {
        UpdatePersonCommand command = new UpdatePersonCommand(
                request.firstname(), request.lastname(), request.nickname(),
                request.email(), request.role()
        );
        return ResponseEntity.ok(PersonDto.from(updatePersonUseCase.updatePerson(id, command)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft-delete a person (marks status as DELETED)")
    public void deletePerson(@PathVariable UUID id) {
        deletePersonUseCase.deletePerson(id);
    }
}
