package com.accm.comicbook.infrastructure.web;

import com.accm.comicbook.domain.port.in.DeleteEditionUseCase;
import com.accm.comicbook.domain.port.in.GetEditionUseCase;
import com.accm.comicbook.domain.port.in.UpdateEditionUseCase;
import com.accm.comicbook.infrastructure.web.dto.EditionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
class EditionController implements EditionApi {

    private final GetEditionUseCase getEditionUseCase;
    private final UpdateEditionUseCase updateEditionUseCase;
    private final DeleteEditionUseCase deleteEditionUseCase;
    private final EditionWebMapper editionWebMapper;

    @Override
    public ResponseEntity<EditionDto> getEdition(UUID id) {
        return ResponseEntity.ok(editionWebMapper.toDto(getEditionUseCase.getEditionById(id)));
    }

    @Override
    public ResponseEntity<EditionDto> updateEdition(UUID id, EditionDto request) {
        return ResponseEntity.ok(
                editionWebMapper.toDto(updateEditionUseCase.updateEdition(id, editionWebMapper.toDomain(request))));
    }

    @Override
    public void deleteEdition(UUID id) {
        deleteEditionUseCase.deleteEdition(id);
    }
}
