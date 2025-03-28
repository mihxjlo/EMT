package mk.finki.ukim.mk.airbnb.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.finki.ukim.mk.airbnb.dto.HostDto;
import mk.finki.ukim.mk.airbnb.service.HostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hosts")
@Tag(name = "Host API", description = "API for managing hosts")
public class HostController {

    private final HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @GetMapping
    @Operation(summary = "Get all hosts", description = "Returns a list of all hosts")
    public List<HostDto> getAllHosts() {
        return hostService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get host by ID", description = "Returns a host by its ID")
    public ResponseEntity<HostDto> getHostById(@PathVariable Long id) {
        return hostService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new host", description = "Creates a new host")
    public HostDto createHost(@RequestBody HostDto hostDto) {
        return hostService.save(hostDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a host", description = "Updates an existing host")
    public ResponseEntity<HostDto> updateHost(@PathVariable Long id, @RequestBody HostDto hostDto) {
        if (!id.equals(hostDto.id())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(hostService.save(hostDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a host", description = "Deletes a host by its ID")
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {
        hostService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 