package com.accm.referential.infrastructure.persistence;

import com.accm.referential.domain.model.Region;
import com.accm.referential.domain.port.out.RegionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class RegionPersistenceAdapter implements RegionRepositoryPort {

    private final RegionJpaRepository repository;
    private final RegionEntityMapper mapper;

    @Override
    public Region save(Region region) {
        RegionJpaEntity entity = repository.findById(region.code()).orElseGet(RegionJpaEntity::new);
        mapper.updateEntity(entity, region);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Region> findByCode(Integer code) {
        return repository.findById(code).map(mapper::toDomain);
    }

    @Override
    public List<Region> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Integer code) {
        repository.deleteById(code);
    }
}
