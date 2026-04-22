package com.accm.referential.infrastructure.persistence;

import com.accm.referential.domain.model.Country;
import com.accm.referential.domain.port.out.CountryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class CountryPersistenceAdapter implements CountryRepositoryPort {

    private final CountryJpaRepository repository;
    private final RegionJpaRepository regionRepository;
    private final SubRegionJpaRepository subRegionRepository;
    private final CountryEntityMapper mapper;

    @Override
    public Country save(Country country) {
        CountryJpaEntity entity = repository.findById(country.countryCode()).orElseGet(CountryJpaEntity::new);
        mapper.updateEntity(entity, country);
        entity.setRegion(country.regionCode() != null ? regionRepository.getReferenceById(country.regionCode()) : null);
        entity.setSubRegion(country.subRegionCode() != null ? subRegionRepository.getReferenceById(country.subRegionCode()) : null);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Country> findByCountryCode(Integer countryCode) {
        return repository.findById(countryCode).map(mapper::toDomain);
    }

    @Override
    public List<Country> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Integer countryCode) {
        repository.deleteById(countryCode);
    }
}
