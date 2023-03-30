package pl.pelikan.pelikanbe.photo;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;

import java.util.List;

@RestController
@RequestMapping("/photos")
@AllArgsConstructor
public class PhotoController {

    private final PhotoService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable Long id) {
        if (service.existsPhotoById(id)) {
            return ResponseEntity.ok(service.getPhotoById(id));
        } else {
            throw new EntityNotFoundException("Photo " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Photo>> getPhotos() {
        return ResponseEntity.ok(service.getPhotos());
    }

    @PostMapping("/add")
    public ResponseEntity<Photo> addPhoto(@RequestBody Photo photo) {
        return ResponseEntity.ok(service.addPhoto(photo));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Photo> updatePhotoById(@PathVariable Long id, @RequestBody Photo photo) throws InvalidIdException {
        if (service.existsPhotoById(id)) {
            if (id.equals(photo.getId())) {
                return ResponseEntity.ok(service.updatePhoto(photo));
            } else {
                throw new InvalidIdException(id + " " + photo.getId());
            }
        } else {
            throw new EntityNotFoundException("Photo " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhotoById(@PathVariable Long id) {
        if (service.existsPhotoById(id)) {
            service.deletePhoto(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Photo " + id);
        }
    }
}
