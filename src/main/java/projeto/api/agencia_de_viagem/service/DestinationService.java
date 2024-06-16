package projeto.api.agencia_de_viagem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.api.agencia_de_viagem.model.Destination;
import projeto.api.agencia_de_viagem.repository.DestinationRepository;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public Destination save(Destination destination) {
        return destinationRepository.save(destination);
    }

    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }
    public List<Destination> search(String query) {
        return destinationRepository.findByNameContainingOrLocationContaining(query, query);
    }
    public Optional<Destination> findById(Long id) {
        return destinationRepository.findById(id);
    }

    public void deleteById(Long id) {
        destinationRepository.deleteById(id);
    }

    public Destination rateDestination(Long id, Double rating) {
        Destination destination = destinationRepository.findById(id).orElseThrow();
        destination.setRating((destination.getRating() * destination.getRatingCount() + rating) / (destination.getRatingCount() + 1));
        destination.setRatingCount(destination.getRatingCount() + 1);
        return destinationRepository.save(destination);
    }
}