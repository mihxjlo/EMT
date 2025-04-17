package mk.finki.ukim.mk.airbnb.service;

import mk.finki.ukim.mk.airbnb.dto.UserDto;
import mk.finki.ukim.mk.airbnb.dto.UserLoginDto;
import mk.finki.ukim.mk.airbnb.dto.UserRegisterDto;

public interface AuthService {
UserDto register(UserRegisterDto registerDto);
UserDto login(UserLoginDto loginDto);
UserDto getCurrentUser();
}
