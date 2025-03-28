package mk.finki.ukim.mk.airbnb.service.impl;

import mk.finki.ukim.mk.airbnb.dto.AccommodationDto;
import mk.finki.ukim.mk.airbnb.models.domain.Accommodation;
import mk.finki.ukim.mk.airbnb.repository.AccomodationRepository;
import mk.finki.ukim.mk.airbnb.service.AccommodationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    private final AccomodationRepository accommodationRepository;

    public AccommodationServiceImpl(AccomodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<AccommodationDto> findAll() {
        return accommodationRepository.findAll().stream()
                .map(AccommodationDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccommodationDto> findById(Long id) {
        return accommodationRepository.findById(id)
                .map(AccommodationDto::new);
    }

    @Override
    public AccommodationDto save(AccommodationDto accommodationDto) {
        Accommodation accommodation = accommodationDto.toEntity();
        accommodation = accommodationRepository.save(accommodation);
        return new AccommodationDto(accommodation);
    }

    @Override
    public void deleteById(Long id) {
        accommodationRepository.deleteById(id);
    }

    @Override
    public AccommodationDto markAsRented(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accommodation not found with id: " + id));
        
        accommodation.setRented(true);
        accommodation = accommodationRepository.save(accommodation);
        
        return new AccommodationDto(accommodation);
    }
} 