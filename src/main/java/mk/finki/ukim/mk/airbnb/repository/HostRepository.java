package mk.finki.ukim.mk.airbnb.repository;

import mk.finki.ukim.mk.airbnb.models.domain.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {
}
