package com.accm.referential.infrastructure.persistence;

import com.accm.referential.domain.model.Country;
import com.accm.referential.domain.port.out.CountryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class CountryPersistenceAdapter implements CountryRepositoryPort {

    private final CountryJpaRepository repository;
    private final CountryEntityMapper mapper;

    @Override
    public Country save(Country country) {
        CountryJpaEntity entity = country.id() != null
                ? repository.findById(country.id()).orElseGet(CountryJpaEntity::new)
                : new CountryJpaEntity();
        mapper.updateEntity(entity, country);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Country> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Country> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
