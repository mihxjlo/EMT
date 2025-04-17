package mk.finki.ukim.mk.airbnb.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.finki.ukim.mk.airbnb.dto.TempReservationDto;
import mk.finki.ukim.mk.airbnb.service.TempReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temp-reservations")
@Tag(name = "Temporary Reservations", description = "Operations related to temporary reservations")
public class TempReservationController{

    public final TempReservationService tempReservationService;

    public TempReservationController(TempReservationService tempReservationService) {
        this.tempReservationService = tempReservationService;
    }

    @GetMapping
    @Operation(summary = "Get user's temporary reservations",
                description = "Returns all temporary reservations for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PreAuthorize("hasAnyRole('USER','HOST')")
    public ResponseEntity<List<TempReservationDto>> getUserTempReservations() {
        return ResponseEntity.ok(tempReservationService.getUserTempReservations());
    }

    @PostMapping("/{accommodationId}")
    @Operation(summary = "Add accommodation to temporary reservations", description = "Adds the specified reservation to the user's temporary reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added to temporary reservations"),
            @ApiResponse(responseCode = "400", description = "Accommodation is not available"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PreAuthorize("hasAnyRole('USER','HOST')")
    public ResponseEntity<TempReservationDto> addToTempReservations(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(tempReservationService.addToTempReservations(accommodationId));
    }

    @DeleteMapping("/{tempReservationId}")
    @Operation(summary="Remove the temporary reservation", description = "Removes the specified reservation from the user's temporary reservations list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed from temporary reservations"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Temporary reservation not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PreAuthorize("hasAnyRole('USER', 'HOST')")
    public ResponseEntity<Void> removeFromTempReservations(@PathVariable Long tempReservationId) {
        tempReservationService.removeFromTempReservations(tempReservationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/confirm")
    @Operation(summary = "Confirm all temporary reservations", description = "Converts all items in the temporary reservations list to actual reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully confirmed all reservations"),
            @ApiResponse(responseCode = "400", description = "No temporary reservations found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PreAuthorize("hasAnyRole('USER', 'HOST')")
    public ResponseEntity<List<TempReservationDto>> confirmAllTempReservations(@RequestParam Long userId) {
        return ResponseEntity.ok(tempReservationService.confirmAllTempReservations(userId));
    }




}
