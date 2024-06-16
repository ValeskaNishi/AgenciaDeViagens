package projeto.api.agencia_de_viagem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projeto.api.agencia_de_viagem.model.Destination;
import projeto.api.agencia_de_viagem.service.DestinationService;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @PostMapping
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        return ResponseEntity.ok(destinationService.save(destination));
    }

    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        return ResponseEntity.ok(destinationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        return destinationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Destination>> searchDestinations(@RequestParam String query) {
        return ResponseEntity.ok(destinationService.search(query));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<Destination> rateDestination(@PathVariable Long id, @RequestParam Double rating) {
        return ResponseEntity.ok(destinationService.rateDestination(id, rating));
    }
     @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destination) {
        return destinationService.findById(id)
                .map(existingDestination -> {
                    existingDestination.setName(destination.getName());
                    existingDestination.setLocation(destination.getLocation());
                    existingDestination.setDescription(destination.getDescription());
                    existingDestination.setRating(destination.getRating());
                    existingDestination.setRatingCount(destination.getRatingCount());
                    Destination updatedDestination = destinationService.save(existingDestination);
                    return ResponseEntity.ok(updatedDestination);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}