package mk.finki.ukim.mk.airbnb.dto;

import mk.finki.ukim.mk.airbnb.models.domain.Host;

public record HostDto(Long id,
                      String name,
                      String surname,
                      CountryDto country) {

    public HostDto(Host host) {
        this(
                host.getId(),
                host.getName(),
                host.getSurname(),
                host.getCountry() != null ? new CountryDto(host.getCountry()) : null
        );
    }

    // Метод за конвертирање во entity
    public Host toEntity() {
        Host host = new Host();
        host.setId(this.id);
        host.setName(this.name);
        host.setSurname(this.surname);
        host.setCountry(this.country != null ? this.country.toEntity() : null);
        return host;
    }

}
