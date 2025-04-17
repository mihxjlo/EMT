package mk.finki.ukim.mk.airbnb.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateReservationDto(Long accommodationId, Long userId, LocalDateTime checkInDate, LocalDateTime checkOutDate, Integer numOfGuests) {
}
