package pl.pelikan.pelikanbe.hashtag;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;

import java.util.List;

@RestController
@RequestMapping("/hashtags")
@AllArgsConstructor
public class HashtagController {

    private HashtagService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<Hashtag> getHashtagById(@PathVariable Long id) {
        if (service.existsHashtagById(id)) {
            return ResponseEntity.ok(service.getHashtagById(id));
        } else {
            throw new EntityNotFoundException("Hashtag " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Hashtag>> getHashtags() {
        return ResponseEntity.ok(service.getHashtags());
    }

    @PostMapping("/add")
    public ResponseEntity<Hashtag> addHashtag(@RequestBody Hashtag hashtag) {
        return ResponseEntity.ok(service.addHashtag(hashtag));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Hashtag> updateHashtagById(@PathVariable Long id, @RequestBody Hashtag hashtag) throws InvalidIdException {
        if (service.existsHashtagById(id)) {
            if (id.equals(hashtag.getId())) {
                return ResponseEntity.ok(service.updateHashtag(hashtag));
            } else {
                throw new InvalidIdException(id + " " + hashtag.getId());
            }
        } else {
            throw new EntityNotFoundException("Hashtag " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHashtagById(@PathVariable Long id) {
        if (service.existsHashtagById(id)) {
            service.deleteHashtag(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Hashtag " + id);
        }
    }
}
