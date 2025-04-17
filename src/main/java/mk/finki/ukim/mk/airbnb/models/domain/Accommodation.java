package mk.finki.ukim.mk.airbnb.models.domain;

import jakarta.persistence.*;
import lombok.Data;
import mk.finki.ukim.mk.airbnb.models.enumerations.AccommodationType;

//@Data
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private AccommodationType category;

    @ManyToOne
    @JoinColumn(name="host_id")
    private Host host;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    private Integer numRooms;
    private boolean isRented = false;





    // Manual getters
    public Country getCountry() {
        return country;
    }

   public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public AccommodationType getCategory() {
        return category;
    }
    
    public Host getHost() {
        return host;
    }
    
    public Integer getNumRooms() {
        return numRooms;
    }
    
    public boolean isRented() {
        return isRented;
    }
    
    // Manual setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public void setCountry(Country country) {
        this.country = country;
    }
    public void setCategory(AccommodationType category) {
        this.category = category;
    }
    
    public void setHost(Host host) {
        this.host = host;
    }
    
    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }
    
    public void setRented(boolean isRented) {
        this.isRented = isRented;
    }

    public boolean getIsRented() {
        return isRented;
    }
    public void setIsRented(boolean isRented) {
        this.isRented = isRented;
    }
}
