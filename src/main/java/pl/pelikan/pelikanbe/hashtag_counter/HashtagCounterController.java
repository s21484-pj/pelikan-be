package pl.pelikan.pelikanbe.hashtag_counter;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;

import java.util.List;

@RestController
@RequestMapping("/hashtagCounters")
@AllArgsConstructor
public class HashtagCounterController {

    private final HashtagCounterService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<HashtagCounter> getHashtagCounterById(@PathVariable Long id) {
        if (service.existsHashtagCounterById(id)) {
            return ResponseEntity.ok(service.getHashtagCounterById(id));
        } else {
            throw new EntityNotFoundException("HashtagCounter " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<HashtagCounter>> getHashtagCounters() {
        return ResponseEntity.ok(service.getHashtagCounters());
    }

    @PostMapping("/add")
    public ResponseEntity<HashtagCounter> addHashtagCounter(@RequestBody HashtagCounter hashtagCounter) {
        return ResponseEntity.ok(service.addHashtagCounter(hashtagCounter));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashtagCounter> updateHashtagCounterById(@PathVariable Long id, @RequestBody HashtagCounter hashtagCounter) throws InvalidIdException {
        if (service.existsHashtagCounterById(id)) {
            if (id.equals(hashtagCounter.getId())) {
                return ResponseEntity.ok(service.updateHashtagCounter(hashtagCounter));
            } else {
                throw new InvalidIdException(id + " " + hashtagCounter.getId());
            }
        } else {
            throw new EntityNotFoundException("HashtagCounter " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHashtagCounterById(@PathVariable Long id) {
        if (service.existsHashtagCounterById(id)) {
            service.deleteHashtagCounter(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("HashtagCounter " + id);
        }
    }
}
