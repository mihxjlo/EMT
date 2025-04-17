package mk.finki.ukim.mk.airbnb.dto;

import mk.finki.ukim.mk.airbnb.models.enumerations.AccommodationType;

public record CreateAccommodationDto(Long accommodationId, String name, AccommodationType accommodationType,Integer numRooms,boolean isRented) {
}
