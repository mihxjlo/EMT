package mk.finki.ukim.mk.airbnb.dto;

import java.util.Map;

public record AccommodationStatisticsDto(Long totalAccommodations,Map<String,Long>accommodationsByType) {
}
