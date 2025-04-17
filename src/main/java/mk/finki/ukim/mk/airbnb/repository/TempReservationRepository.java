package mk.finki.ukim.mk.airbnb.repository;

import mk.finki.ukim.mk.airbnb.models.domain.Accommodation;
import mk.finki.ukim.mk.airbnb.models.domain.TempReservation;
import mk.finki.ukim.mk.airbnb.models.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface TempReservationRepository extends JpaRepository<TempReservation, Long> {
    List<TempReservation> findByUserId(User user);
    Optional<TempReservation> findByUserAndAccommodation(User user, Accommodation accommodation);
    void deleteByUser(User user);

}
