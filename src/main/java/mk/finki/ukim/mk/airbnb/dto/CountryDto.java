package mk.finki.ukim.mk.airbnb.dto;

import mk.finki.ukim.mk.airbnb.models.domain.Country;

public record CountryDto(Long id,
                         String name,
                         String continent) {

    public CountryDto(Country country) {
        this(
                country.getId(),
                country.getName(),
                country.getContinent()
        );
    }

    // Метод за конвертирање во entity
    public Country toEntity() {
        Country country = new Country();
        country.setId(this.id);
        country.setName(this.name);
        country.setContinent(this.continent);
        return country;
    }
}
