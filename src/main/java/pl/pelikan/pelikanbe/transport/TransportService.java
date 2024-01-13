package pl.pelikan.pelikanbe.transport;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.offer.Offer;

import java.util.List;

@Service
@AllArgsConstructor
public class TransportService {

    private final TransportRepository repository;

    public Transport getTransportById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Transport> getTransports() {
        return repository.findAll();
    }

    public Transport addTransport(Transport transport) {
        return repository.save(transport);
    }

    public Transport updateTransport(Transport transport) {
        return repository.save(transport);
    }

    public void deleteTransport(Long id) {
        Transport transport = getTransportById(id);
        if (transport != null) {
            if (transport.getOffer() != null) {
                Offer offer = transport.getOffer();
                offer.setTransport(null);
            }
            repository.deleteById(id);
        }
    }

    public boolean existsTransportById(Long id) {
        return repository.existsById(id);
    }
}
