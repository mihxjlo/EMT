package mk.finki.ukim.mk.airbnb.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.finki.ukim.mk.airbnb.dto.AccommodationStatisticsDto;
import mk.finki.ukim.mk.airbnb.service.AccommodationService;
import mk.finki.ukim.mk.airbnb.service.AccommodationStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@Tag(name="Statistics",description = "API for statistics")
public class AccommodationStatisticsController {
    private final AccommodationStatisticsService accommodationService;

    public AccommodationStatisticsController(AccommodationStatisticsService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping("/accommodations")
    @Operation(summary = "Get accommodations statistics", description = "Return the total number of accommodations and count by type")
    @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully",
            content = @Content(schema = @Schema(implementation = AccommodationStatisticsDto.class)))
    public ResponseEntity<AccommodationStatisticsDto> getAccommodationStatistics() {
        AccommodationStatisticsDto stats = accommodationService.getAccommodationStatistics();
        return ResponseEntity.ok(stats);
    }
}
