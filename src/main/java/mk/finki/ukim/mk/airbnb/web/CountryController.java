package mk.finki.ukim.mk.airbnb.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.finki.ukim.mk.airbnb.dto.CountryDto;
import mk.finki.ukim.mk.airbnb.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Country API", description = "API for managing countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    @Operation(summary = "Get all countries", description = "Returns a list of all countries")
    public List<CountryDto> getAllCountries() {
        return countryService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get country by ID", description = "Returns a country by its ID")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        return countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new country", description = "Creates a new country")
    public CountryDto createCountry(@RequestBody CountryDto countryDto) {
        return countryService.save(countryDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a country", description = "Updates an existing country")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        if (!id.equals(countryDto.id())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(countryService.save(countryDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a country", description = "Deletes a country by its ID")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 