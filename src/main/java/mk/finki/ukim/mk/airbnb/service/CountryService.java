package mk.finki.ukim.mk.airbnb.service;

import mk.finki.ukim.mk.airbnb.dto.CountryDto;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<CountryDto> findAll();
    Optional<CountryDto> findById(Long id);
    CountryDto save(CountryDto countryDto);
    void deleteById(Long id);
} 