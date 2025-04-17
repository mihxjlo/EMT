package mk.finki.ukim.mk.airbnb.dto;

import mk.finki.ukim.mk.airbnb.models.enumerations.ReservationStatus;
import java.time.LocalDate;

public record ReservationDto(
    Long id,
    Long userId,
    String username,
    AccommodationDto accommodation,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    LocalDate createdAt,
    ReservationStatus status
) {}
