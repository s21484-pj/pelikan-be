package pl.pelikan.pelikanbe.transport;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transports")
public class TransportController {

    private final TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Transport> getTransportById(@PathVariable Long id) {
        if (transportService.existTransportById(id)) {
            return ResponseEntity.ok(transportService.getTransportById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Transport>> getTransports() {
        return ResponseEntity.ok(transportService.getTransports());
    }

    @PostMapping("/add")
    public ResponseEntity<Transport> addTransport(@RequestBody Transport transport) {
        return ResponseEntity.ok(transportService.addTransport(transport));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Transport> updateTransportById(@PathVariable Long id, @RequestBody Transport transport) {
        if (transportService.existTransportById(id)) {
            return ResponseEntity.ok(transportService.updateTransportById(id, transport));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransportById(@PathVariable Long id) {
        if (transportService.existTransportById(id)) {
            transportService.deleteTransportById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
