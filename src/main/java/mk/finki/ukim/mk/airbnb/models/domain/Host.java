package mk.finki.ukim.mk.airbnb.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

//@Data
@Entity
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    
    // Manual getters
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public Country getCountry() {
        return country;
    }
    
    // Manual setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }
}
