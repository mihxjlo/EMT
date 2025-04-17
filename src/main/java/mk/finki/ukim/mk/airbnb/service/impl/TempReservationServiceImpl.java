package mk.finki.ukim.mk.airbnb.service.impl;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.airbnb.dto.AccommodationDto;
import mk.finki.ukim.mk.airbnb.dto.HostDto;
import mk.finki.ukim.mk.airbnb.dto.TempReservationDto;
import mk.finki.ukim.mk.airbnb.models.domain.Accommodation;
import mk.finki.ukim.mk.airbnb.models.domain.Reservation;
import mk.finki.ukim.mk.airbnb.models.domain.TempReservation;
import mk.finki.ukim.mk.airbnb.models.domain.User;
import mk.finki.ukim.mk.airbnb.repository.AccommodationRepository;
import mk.finki.ukim.mk.airbnb.repository.ReservationRepository;
import mk.finki.ukim.mk.airbnb.repository.TempReservationRepository;
import mk.finki.ukim.mk.airbnb.repository.UserRepository;
import mk.finki.ukim.mk.airbnb.service.TempReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempReservationServiceImpl implements TempReservationService {

    private final TempReservationRepository tempReservationRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;

    public TempReservationServiceImpl(
            TempReservationRepository tempReservationRepository,
            UserRepository userRepository,
            ReservationRepository reservationRepository, AccommodationRepository accommodationRepository) {
        this.tempReservationRepository = tempReservationRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<TempReservationDto> getUserTempReservations() {
        User currentUser = getCurrentUser();
        List<TempReservation> tempReservations = tempReservationRepository.findByUserId(currentUser);
        return tempReservations.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TempReservationDto addToTempReservations(Long accommodationId) {
        User currentUser = getCurrentUser();
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(()-> new RuntimeException("Accommodation not found."));

        if (!accommodation.getIsRented()) {
            throw new RuntimeException("Accommodation is not available");
        }

        tempReservationRepository.findByUserAndAccommodation(currentUser,accommodation)
                .ifPresent(tr->{throw new RuntimeException("Accommodation already in repository!");
                });

        TempReservation tempReservation = new TempReservation(currentUser,accommodation);
        TempReservation saved = tempReservationRepository.save(tempReservation);

        return mapToDto(saved);
    }

    @Override
    public void removeFromTempReservations(Long tempReservationId) {
        User currentUser = getCurrentUser();
        TempReservation tempReservation = tempReservationRepository.findById(tempReservationId)
                .orElseThrow(()-> new RuntimeException("Temp reservation not found."));

        if(!tempReservation.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to remove this temporary reservation");
        }

        tempReservationRepository.delete(tempReservation);
    }

    @Override
    @Transactional
    public List<TempReservationDto> confirmAllTempReservations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found."));

        List<TempReservation> tempReservations = tempReservationRepository.findByUserId(user);

        if(tempReservations.isEmpty()) {
            throw new RuntimeException("Temp reservation not found");
        }

        List<TempReservationDto> confirmed = tempReservations.stream()
                .map(tr -> {
                    Accommodation accommodation = tr.getAccommodation();

                if(!accommodation.getIsRented()){
                    throw new RuntimeException("Accommodation "+accommodation.getName()+" is not available");
                }

                accommodation.setIsRented(false);
                accommodationRepository.save(accommodation);

                LocalDate checkInDate = LocalDate.now();
                LocalDate checkOutDate = checkInDate.plusDays(7);

                Reservation reservation = new Reservation(user,accommodation,checkInDate,checkOutDate);
                reservationRepository.save(reservation);

                return mapToDto(tr);
                })
                .collect(Collectors.toList());

        tempReservationRepository.deleteByUser(user);

        return confirmed;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    private TempReservationDto mapToDto(TempReservation tempReservation) {
        return new TempReservationDto(
                tempReservation.getId(),
                tempReservation.getUser().getId(),
                tempReservation.getUser().getUsername(),
                mapAccommodationDto(tempReservation.getAccommodation()),
                tempReservation.getCreatedAt()
        );
    }

    private AccommodationDto mapAccommodationDto(Accommodation accommodation) {

        return new AccommodationDto(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getCategory(),
                accommodation.getHost() != null ? new HostDto(accommodation.getHost()) : null,
                accommodation.getNumRooms(),
                accommodation.isRented()
        );
    }
}
