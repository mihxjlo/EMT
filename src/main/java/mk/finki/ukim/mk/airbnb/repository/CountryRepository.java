package mk.finki.ukim.mk.airbnb.repository;

import mk.finki.ukim.mk.airbnb.models.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
