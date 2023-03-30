package pl.pelikan.pelikanbe.offer;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;

import java.util.List;

@RestController
@RequestMapping("/offers")
@AllArgsConstructor
public class OfferController {

    private final OfferService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        if (service.existsOfferById(id)) {
            return ResponseEntity.ok(service.getOfferById(id));
        } else {
            throw new EntityNotFoundException("Offer " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Offer>> getOffers() {
        return ResponseEntity.ok(service.getOffers());
    }

    @PostMapping("/add")
    public ResponseEntity<Offer> addOffer(@RequestBody Offer offer) {
        return ResponseEntity.ok(service.addOffer(offer));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Offer> updateOfferById(@PathVariable Long id, @RequestBody Offer offer) throws InvalidIdException {
        if (service.existsOfferById(id)) {
            if (id.equals(offer.getId())) {
                return ResponseEntity.ok(service.updateOffer(offer));
            } else {
                throw new InvalidIdException(id + " " + offer.getId());
            }
        } else {
            throw new EntityNotFoundException("Offer " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOfferById(@PathVariable Long id) {
        if (service.existsOfferById(id)) {
            service.deleteOffer(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Offer " + id);
        }
    }
}