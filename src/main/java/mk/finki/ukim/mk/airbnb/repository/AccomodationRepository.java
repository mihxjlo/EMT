package mk.finki.ukim.mk.airbnb.repository;

import mk.finki.ukim.mk.airbnb.models.domain.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccomodationRepository extends JpaRepository<Accommodation, Long> {
}
