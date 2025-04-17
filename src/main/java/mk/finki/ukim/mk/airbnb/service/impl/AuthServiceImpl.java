package mk.finki.ukim.mk.airbnb.service.impl;

import mk.finki.ukim.mk.airbnb.dto.UserDto;
import mk.finki.ukim.mk.airbnb.dto.UserLoginDto;
import mk.finki.ukim.mk.airbnb.dto.UserRegisterDto;
import mk.finki.ukim.mk.airbnb.models.domain.User;
import mk.finki.ukim.mk.airbnb.repository.UserRepository;
import mk.finki.ukim.mk.airbnb.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto register(UserRegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.username())){
            throw new RuntimeException("Username is already in use");
        }

        if(userRepository.existsByEmail(registerDto.email())){
            throw new RuntimeException("Email is already in use");
        }

        User user = new User(registerDto.username(),
                passwordEncoder.encode(registerDto.password()),
                registerDto.email(),
                registerDto.role()
        );
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    @Override
    public UserDto login(UserLoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.username(),
                loginDto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(loginDto.username())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    @Override
    public UserDto getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDto(user);
    }

    private UserDto mapToDto(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }

        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }
}
