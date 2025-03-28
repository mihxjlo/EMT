package mk.finki.ukim.mk.airbnb.service.impl;

import mk.finki.ukim.mk.airbnb.dto.CountryDto;
import mk.finki.ukim.mk.airbnb.models.domain.Country;
import mk.finki.ukim.mk.airbnb.repository.CountryRepository;
import mk.finki.ukim.mk.airbnb.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryDto> findAll() {
        return countryRepository.findAll().stream()
                .map(CountryDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CountryDto> findById(Long id) {
        return countryRepository.findById(id)
                .map(CountryDto::new);
    }

    @Override
    public CountryDto save(CountryDto countryDto) {
        Country country = countryDto.toEntity();
        country = countryRepository.save(country);
        return new CountryDto(country);
    }

    @Override
    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }
} 