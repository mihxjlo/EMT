package mk.finki.ukim.mk.airbnb.config;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.airbnb.dto.AccommodationDto;
import mk.finki.ukim.mk.airbnb.dto.CountryDto;
import mk.finki.ukim.mk.airbnb.dto.HostDto;
import mk.finki.ukim.mk.airbnb.models.enumerations.AccommodationType;
import mk.finki.ukim.mk.airbnb.service.AccommodationService;
import mk.finki.ukim.mk.airbnb.service.CountryService;
import mk.finki.ukim.mk.airbnb.service.HostService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final CountryService countryService;
    private final HostService hostService;
    private final AccommodationService accommodationService;

    public DataInitializer(CountryService countryService, 
                          HostService hostService, 
                          AccommodationService accommodationService) {
        this.countryService = countryService;
        this.hostService = hostService;
        this.accommodationService = accommodationService;
    }

    @PostConstruct
    public void initData() {
        // Add countries
        CountryDto northMacedonia = countryService.save(new CountryDto(null, "North Macedonia", "Europe"));
        CountryDto serbia = countryService.save(new CountryDto(null, "Serbia", "Europe"));
        CountryDto italy = countryService.save(new CountryDto(null, "Italy", "Europe"));
        CountryDto france = countryService.save(new CountryDto(null, "France", "Europe"));
        CountryDto usa = countryService.save(new CountryDto(null, "USA", "North America"));
        
        // Add hosts
        HostDto john = hostService.save(new HostDto(null, "John", "Smith", usa));
        HostDto marie = hostService.save(new HostDto(null, "Marie", "Dupont", france));
        HostDto nikola = hostService.save(new HostDto(null, "Nikola", "Nikolić", serbia));
        HostDto marco = hostService.save(new HostDto(null, "Marco", "Rossi", italy));
        HostDto ana = hostService.save(new HostDto(null, "Ana", "Jovanovska", northMacedonia));
        
        // Add accommodations
        accommodationService.save(new AccommodationDto(
                null, 
                "Luxury Villa in Los Angeles", 
                AccommodationType.HOUSE, 
                john, 
                5, 
                false
        ));
        
        accommodationService.save(new AccommodationDto(
                null, 
                "Cozy Apartment in Paris", 
                AccommodationType.APARTMENT, 
                marie, 
                2, 
                false
        ));
        
        accommodationService.save(new AccommodationDto(
                null, 
                "Belgrade City Center Room", 
                AccommodationType.ROOM, 
                nikola, 
                1, 
                true
        ));
        
        accommodationService.save(new AccommodationDto(
                null, 
                "Tuscan Villa with Pool", 
                AccommodationType.HOUSE, 
                marco, 
                6, 
                false
        ));
        
        accommodationService.save(new AccommodationDto(
                null, 
                "Ohrid Lake Apartment", 
                AccommodationType.APARTMENT, 
                ana, 
                3, 
                false
        ));
        
        accommodationService.save(new AccommodationDto(
                null, 
                "Skopje City Hotel", 
                AccommodationType.HOTEL, 
                ana, 
                20, 
                false
        ));
    }
} 