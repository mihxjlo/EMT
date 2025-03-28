package mk.finki.ukim.mk.airbnb.service;

import mk.finki.ukim.mk.airbnb.dto.AccommodationDto;

import java.util.List;
import java.util.Optional;

public interface AccommodationService {
    List<AccommodationDto> findAll();
    Optional<AccommodationDto> findById(Long id);
    AccommodationDto save(AccommodationDto accommodationDto);
    void deleteById(Long id);
    AccommodationDto markAsRented(Long id);
} 