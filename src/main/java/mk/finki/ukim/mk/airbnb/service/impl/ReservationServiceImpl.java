package mk.finki.ukim.mk.airbnb.service.impl;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.airbnb.dto.*;
import mk.finki.ukim.mk.airbnb.models.domain.Accommodation;
import mk.finki.ukim.mk.airbnb.models.domain.Reservation;
import mk.finki.ukim.mk.airbnb.models.domain.User;
import mk.finki.ukim.mk.airbnb.models.enumerations.ReservationStatus;
import mk.finki.ukim.mk.airbnb.repository.AccommodationRepository;
import mk.finki.ukim.mk.airbnb.repository.ReservationRepository;
import mk.finki.ukim.mk.airbnb.repository.UserRepository;
import mk.finki.ukim.mk.airbnb.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, AccommodationRepository AccommodationRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.accommodationRepository = AccommodationRepository;
    }

    @Override
    @Transactional
    public ReservationDto createReservation(CreateReservationDto createReservationDto) {
        User user = userRepository.findById(createReservationDto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Accommodation accommodation = accommodationRepository.findById(createReservationDto.accommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        if(!isAccommodationAvailable(accommodation.getId(),
                createReservationDto.checkInDate().toLocalDate(),
                createReservationDto.checkOutDate().toLocalDate())) {
            throw new RuntimeException("Accommodation is not available for the selected dates");
        }

        Reservation reservation = new Reservation(
            user,
            accommodation,
            createReservationDto.checkInDate().toLocalDate(),
            createReservationDto.checkOutDate().toLocalDate()
        );
        
        reservation.setNumberOfGuests(createReservationDto.numOfGuests());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToDto(savedReservation);
    }

    @Override
    @Transactional
    public ReservationDto updateReservation(Long id, CreateReservationDto createReservationDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found!"));

        User user = userRepository.findById(createReservationDto.userId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Accommodation accommodation = accommodationRepository.findById(createReservationDto.accommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found!"));

        if(!reservation.getCheckInDate().equals(createReservationDto.checkInDate().toLocalDate()) || 
           !reservation.getCheckOutDate().equals(createReservationDto.checkOutDate().toLocalDate())) {
            List<Reservation> conflictingReservations = reservationRepository
                    .findOverlappingReservations(accommodation.getId(),
                            createReservationDto.checkInDate().toLocalDate(),
                            createReservationDto.checkOutDate().toLocalDate())
                    .stream()
                    .filter(r -> !r.getId().equals(id))
                    .collect(Collectors.toList());

            if(!conflictingReservations.isEmpty()) {
                throw new RuntimeException("Accommodation is not available for the selected dates!");
            }
        }

        reservation.setUser(user);
        reservation.setAccommodation(accommodation);
        reservation.setCheckInDate(createReservationDto.checkInDate().toLocalDate());
        reservation.setCheckOutDate(createReservationDto.checkOutDate().toLocalDate());
        reservation.setNumberOfGuests(createReservationDto.numOfGuests());

        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapToDto(updatedReservation);
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found!"));

        reservationRepository.delete(reservation);
    }

    @Override
    public Optional<ReservationDto> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(this::mapToDto);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return reservationRepository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByAccommodationId(Long AccommodationId) {
        Accommodation Accommodation = accommodationRepository.findById(AccommodationId)
                .orElseThrow(() -> new RuntimeException("Accommodation not found!"));
        return reservationRepository.findByAccommodation(Accommodation).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccommodationAvailable(Long AccommodationId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(AccommodationId,
                startDate, endDate);
        return overlappingReservations.isEmpty();
    }

    @Override
    @Transactional
    public ReservationDto confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found!"));

        reservation.setStatus(ReservationStatus.CONFIRMED);
        Reservation confirmedReservation = reservationRepository.save(reservation);

        return mapToDto(confirmedReservation);
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStatus(ReservationStatus.DENIED);
        reservationRepository.save(reservation);
    }

    private ReservationDto mapToDto(Reservation reservation) {
        Accommodation accommodation = reservation.getAccommodation();
        User user = reservation.getUser();

        HostDto hostDto = null;
        if (accommodation.getHost() != null) {
            hostDto = new HostDto(
                accommodation.getHost().getId(),
                accommodation.getHost().getName(),
                accommodation.getHost().getSurname(),
                new CountryDto(accommodation.getHost().getCountry())
            );
        }

        AccommodationDto accommodationDto = new AccommodationDto(
            accommodation.getId(),
            accommodation.getName(),
            accommodation.getCategory(),
            hostDto,
            accommodation.getNumRooms(),
            accommodation.isRented()
        );

        return new ReservationDto(
            reservation.getId(),
            user.getId(),
            user.getUsername(),
            accommodationDto,
            reservation.getCheckInDate(),
            reservation.getCheckOutDate(),
            reservation.getCreatedAt(),
            reservation.getStatus()
        );
    }
}
