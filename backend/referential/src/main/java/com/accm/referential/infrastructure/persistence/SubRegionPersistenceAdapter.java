package com.accm.referential.infrastructure.persistence;

import com.accm.referential.domain.model.SubRegion;
import com.accm.referential.domain.port.out.SubRegionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class SubRegionPersistenceAdapter implements SubRegionRepositoryPort {

    private final SubRegionJpaRepository repository;
    private final RegionJpaRepository regionRepository;
    private final SubRegionEntityMapper mapper;

    @Override
    public SubRegion save(SubRegion subRegion) {
        SubRegionJpaEntity entity = repository.findById(subRegion.code()).orElseGet(SubRegionJpaEntity::new);
        mapper.updateEntity(entity, subRegion);
        entity.setRegion(regionRepository.getReferenceById(subRegion.regionCode()));
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<SubRegion> findByCode(Integer code) {
        return repository.findById(code).map(mapper::toDomain);
    }

    @Override
    public List<SubRegion> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Integer code) {
        repository.deleteById(code);
    }
}
