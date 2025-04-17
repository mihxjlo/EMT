package mk.finki.ukim.mk.airbnb.service;

import mk.finki.ukim.mk.airbnb.dto.TempReservationDto;

import java.util.List;

public interface TempReservationService {
    List<TempReservationDto> getUserTempReservations();
    TempReservationDto addToTempReservations(Long accommodationId);
    void removeFromTempReservations(Long tempReservationId);
    List<TempReservationDto>confirmAllTempReservations(Long userId);
}
