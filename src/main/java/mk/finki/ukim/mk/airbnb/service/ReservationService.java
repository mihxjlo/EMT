package mk.finki.ukim.mk.airbnb.service;

import mk.finki.ukim.mk.airbnb.dto.CreateReservationDto;
import mk.finki.ukim.mk.airbnb.dto.ReservationDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    ReservationDto createReservation(CreateReservationDto createReservationDto);
    ReservationDto updateReservation(Long id,CreateReservationDto createReservationDto);
    void deleteReservation(Long id);

    Optional<ReservationDto> getReservationById(Long id);
    List<ReservationDto> getAllReservations();
    List<ReservationDto> getReservationsByUserId(Long userId);
    List<ReservationDto> getReservationsByAccommodationId(Long accommodationId);
    boolean isAccommodationAvailable(Long accommodationId, LocalDate startDate, LocalDate endDate);
    ReservationDto confirmReservation(Long reservationId);
    void cancelReservation(Long reservationId);
}
