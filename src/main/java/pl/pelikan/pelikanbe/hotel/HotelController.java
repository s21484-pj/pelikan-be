package pl.pelikan.pelikanbe.hotel;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@AllArgsConstructor
public class HotelController {

    private final HotelService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        if (service.existsHotelById(id)) {
            return ResponseEntity.ok(service.getHotelById(id));
        } else {
            throw new EntityNotFoundException("Hotel " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Hotel>> getHotels() {
        return ResponseEntity.ok(service.getHotels());
    }

    @PostMapping("/add")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(service.addHotel(hotel));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Hotel> updateHotelById(@PathVariable Long id, @RequestBody Hotel hotel) throws InvalidIdException {
        if (service.existsHotelById(id)) {
            if (id.equals(hotel.getId())) {
                return ResponseEntity.ok(service.updateHotel(hotel));
            } else {
                throw new InvalidIdException(id + " " + hotel.getId());
            }
        } else {
            throw new EntityNotFoundException("Hotel " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long id) {
        if (service.existsHotelById(id)) {
            service.deleteHotel(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Hotel " + id);
        }
    }
}