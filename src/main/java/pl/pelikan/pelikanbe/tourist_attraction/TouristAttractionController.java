package pl.pelikan.pelikanbe.tourist_attraction;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;

import java.util.List;

@RestController
@RequestMapping("/attractions")
@AllArgsConstructor
public class TouristAttractionController {

    private final TouristAttractionService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<TouristAttraction> getAttractionById(@PathVariable Long id) {
        if (service.existsTouristAttractionById(id)) {
            return ResponseEntity.ok(service.getTouristAttractionById(id));
        } else {
            throw new EntityNotFoundException("Attraction " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<TouristAttraction>> getAttractions() {
        return ResponseEntity.ok(service.getTouristAttractions());
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction attraction) {
        return ResponseEntity.ok(service.addTouristAttraction(attraction));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TouristAttraction> updateAttraction(
            @PathVariable Long id, @RequestBody TouristAttraction attraction) throws InvalidIdException {
        if (service.existsTouristAttractionById(id)) {
            if (id.equals(attraction.getId())) {
                return ResponseEntity.ok(service.updateTouristAttraction(attraction));
            } else {
                throw new InvalidIdException(id + " " + attraction.getId());
            }
        } else {
            throw new EntityNotFoundException("Attraction " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttractionById(@PathVariable Long id) {
        if (service.existsTouristAttractionById(id)) {
            service.deleteTouristAttraction(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Attraction " + id);
        }
    }
}