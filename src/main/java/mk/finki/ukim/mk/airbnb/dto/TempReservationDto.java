package mk.finki.ukim.mk.airbnb.dto;

import java.time.LocalDateTime;

public record TempReservationDto(Long id, Long userId, String username, AccommodationDto accommodation, LocalDateTime createdAt) {
}
