package mk.finki.ukim.mk.airbnb.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.finki.ukim.mk.airbnb.dto.AccommodationDto;
import mk.finki.ukim.mk.airbnb.service.AccommodationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
@Tag(name = "Accommodation API", description = "API for managing accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    @Operation(summary = "Get all accommodations", description = "Returns a list of all accommodations")
    public List<AccommodationDto> getAllAccommodations() {
        return accommodationService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get accommodation by ID", description = "Returns an accommodation by its ID")
    public ResponseEntity<AccommodationDto> getAccommodationById(@PathVariable Long id) {
        return accommodationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new accommodation", description = "Creates a new accommodation")
    public AccommodationDto createAccommodation(@RequestBody AccommodationDto accommodationDto) {
        return accommodationService.save(accommodationDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an accommodation", description = "Updates an existing accommodation")
    public ResponseEntity<AccommodationDto> updateAccommodation(@PathVariable Long id, @RequestBody AccommodationDto accommodationDto) {
        if (!id.equals(accommodationDto.id())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(accommodationService.save(accommodationDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an accommodation", description = "Deletes an accommodation by its ID")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        accommodationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rent")
    @Operation(summary = "Mark accommodation as rented", description = "Marks an accommodation as rented")
    public ResponseEntity<AccommodationDto> markAsRented(@PathVariable Long id) {
        return ResponseEntity.ok(accommodationService.markAsRented(id));
    }
} 