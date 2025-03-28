package mk.finki.ukim.mk.airbnb.models.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

//@Data
@Entity

public class Country {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String continent;
    
    // Manual getters
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getContinent() {
        return continent;
    }
    
    // Manual setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
    }
}
