package mk.finki.ukim.mk.airbnb.dto;

import mk.finki.ukim.mk.airbnb.models.enumerations.UserRole;

import javax.management.relation.Role;

public record UserDto(Long id, String username, String password, UserRole role) {
}
