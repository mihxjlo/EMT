package mk.finki.ukim.mk.airbnb.dto;

import mk.finki.ukim.mk.airbnb.models.domain.Accommodation;
import mk.finki.ukim.mk.airbnb.models.enumerations.AccommodationType;

public record AccommodationDto(Long id,
                              String name,
                              AccommodationType category,
                              HostDto host,
                              Integer numRooms,
                              boolean isRented) {

    public AccommodationDto(Accommodation accommodation) {
        this(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getCategory(),
                accommodation.getHost() != null ? new HostDto(accommodation.getHost()) : null,
                accommodation.getNumRooms(),
                accommodation.isRented()
        );
    }

    // Метод за конвертирање во entity
    public Accommodation toEntity() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(this.id);
        accommodation.setName(this.name);
        accommodation.setCategory(this.category);
        accommodation.setHost(this.host != null ? this.host.toEntity() : null);
        accommodation.setNumRooms(this.numRooms);
        accommodation.setRented(this.isRented);
        return accommodation;
    }
} 