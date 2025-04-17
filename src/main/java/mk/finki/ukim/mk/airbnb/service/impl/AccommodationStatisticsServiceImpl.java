package mk.finki.ukim.mk.airbnb.service.impl;

import mk.finki.ukim.mk.airbnb.dto.AccommodationStatisticsDto;
import mk.finki.ukim.mk.airbnb.models.domain.Accommodation;
import mk.finki.ukim.mk.airbnb.models.enumerations.AccommodationType;
import mk.finki.ukim.mk.airbnb.repository.AccommodationRepository;
import mk.finki.ukim.mk.airbnb.repository.ReservationRepository;
import mk.finki.ukim.mk.airbnb.service.AccommodationStatisticsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccommodationStatisticsServiceImpl implements AccommodationStatisticsService {
    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;

    public AccommodationStatisticsServiceImpl(AccommodationRepository accommodationRepository, ReservationRepository reservationRepository) {
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository = reservationRepository;
    }


    @Override
    public AccommodationStatisticsDto getAccommodationStatistics() {
        Long totalAccommodations = accommodationRepository.count();

        List<Object[]> typeCounts = accommodationRepository.countByAccommodationCategory();
        Map<String,Long> accommodationsByType = new HashMap<>();

        for (Object[] result : typeCounts) {
            AccommodationType type = (AccommodationType) result[0];
            Long count = (Long) result[1];
            accommodationsByType.put(type.toString(),count);
        }

        return new AccommodationStatisticsDto(totalAccommodations, accommodationsByType);


    }
}
