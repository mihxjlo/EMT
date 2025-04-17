package mk.finki.ukim.mk.airbnb.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.finki.ukim.mk.airbnb.models.enumerations.UserRole;

import javax.management.relation.Role;

@Data
@Entity
@Table(name = "app_user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role; // USER,HOST

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    // Explicit getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
}
