package mk.finki.ukim.mk.airbnb.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.finki.ukim.mk.airbnb.dto.UserDto;
import mk.finki.ukim.mk.airbnb.dto.UserLoginDto;
import mk.finki.ukim.mk.airbnb.dto.UserRegisterDto;
import mk.finki.ukim.mk.airbnb.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user with USER or HOST role")
    public ResponseEntity<UserDto> register(@RequestBody UserRegisterDto registerDto) {
        return ResponseEntity.ok(authService.register(registerDto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Authenticates a user and returns user details")
    public ResponseEntity<UserDto> login(@RequestBody UserLoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns details of the currently authenticated user")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
