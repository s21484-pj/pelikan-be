package pl.pelikan.pelikanbe.transport;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;

import java.util.List;

@RestController
@RequestMapping("/transports")
@AllArgsConstructor
public class TransportController {

    private final TransportService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<Transport> getTransportById(@PathVariable Long id) {
        if (service.existsTransportById(id)) {
            return ResponseEntity.ok(service.getTransportById(id));
        } else {
            throw new EntityNotFoundException("Transport " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Transport>> getTransports() {
        return ResponseEntity.ok(service.getTransports());
    }

    @PostMapping("/add")
    public ResponseEntity<Transport> addTransport(@RequestBody Transport transport) {
        return ResponseEntity.ok(service.addTransport(transport));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Transport> updateTransportById(@PathVariable Long id, @RequestBody Transport transport) throws InvalidIdException {
        if (service.existsTransportById(id)) {
            if (id.equals(transport.getId())) {
                return ResponseEntity.ok(service.updateTransport(transport));
            } else {
                throw new InvalidIdException(id + " " + transport.getId());
            }
        } else {
            throw new EntityNotFoundException("Transport " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransportById(@PathVariable Long id) {
        if (service.existsTransportById(id)) {
            service.deleteTransport(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Transport " + id);
        }
    }
}
