package pl.pelikan.pelikanbe.tourist_attraction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attractions")
public class TouristAttractionController {

    private final TouristAttractionService service;

    public TouristAttractionController(TouristAttractionService service) {
        this.service = service;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TouristAttraction> getAttractionById(@PathVariable Long id) {
        if (service.existTouristAttractionById(id)) {
            return ResponseEntity.ok(service.getAttractionById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<TouristAttraction>> getAttractions() {
        return ResponseEntity.ok(service.getAttractions());
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction attraction) {
        return ResponseEntity.ok(service.addAttraction(attraction));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TouristAttraction> updateAttraction(
            @PathVariable Long id, @RequestBody TouristAttraction attraction) {
        if (service.existTouristAttractionById(id)) {
            return ResponseEntity.ok(service.updateById(id, attraction));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttractionById(@PathVariable Long id) {
        if (service.existTouristAttractionById(id)) {
            service.deleteTouristAttractionById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}