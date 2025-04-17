package mk.finki.ukim.mk.airbnb.service;

import mk.finki.ukim.mk.airbnb.models.domain.User;
import mk.finki.ukim.mk.airbnb.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto register(UserDto userDto);
    User getCurrentUser();
    String getCurrentUserId();

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}