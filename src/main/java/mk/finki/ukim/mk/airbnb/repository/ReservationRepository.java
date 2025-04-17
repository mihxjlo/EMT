package mk.finki.ukim.mk.airbnb.repository;

import mk.finki.ukim.mk.airbnb.models.domain.Accommodation;
import mk.finki.ukim.mk.airbnb.models.domain.Reservation;
import mk.finki.ukim.mk.airbnb.models.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);

    @Query("select r FROM Reservation r WHERE r.accommodation = ?1 AND " +
            "((r.checkInDate <= ?2 AND r.checkOutDate >= ?2) OR " +
            "(r.checkInDate <= ?3 AND r.checkOutDate >= ?3) OR " +
            "(r.checkInDate >= ?2 AND r.checkOutDate <= ?3))")
    List<Reservation> findOverlappingReservations(Long accommodation, LocalDate checkInDate, LocalDate checkOutDate);

    Optional<Reservation> findByAccommodation(Accommodation accommodation);
}
