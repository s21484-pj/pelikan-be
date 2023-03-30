package pl.pelikan.pelikanbe.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hotel.HotelService;
import pl.pelikan.pelikanbe.transport.TransportService;

import java.util.List;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    private final HotelService hotelService;

    private final TransportService transportService;

    public Offer getOfferById(Long id) {
        return offerRepository.findById(id).orElse(null);
    }

    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }

    public Offer addOffer(Offer offer) {
        var hotel = offer.getHotel();
        if (hotel != null && hotel.getId() != null) {
            setHotelById(offer, hotel.getId());
        }
        var transport = offer.getTransport();
        if (transport != null && transport.getId() != null) {
            setTransportById(offer, transport.getId());
        }
        return offerRepository.save(offer);
    }

    public Offer updateOffer(Offer offer) {
        var hotel = offer.getHotel();
        if (hotel != null && hotel.getId() != null) {
            setHotelById(offer, hotel.getId());
        }
        var transport = offer.getTransport();
        if (transport != null && transport.getId() != null) {
            setTransportById(offer, transport.getId());
        }
        return offerRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        var offer = getOfferById(id);
        if (offer.getHotel() != null) {
            offer.setHotel(null);
        }
        if (offer.getTransport() != null) {
            offer.setTransport(null);
        }
        if (offer.getAttractions() != null) {
            for (var attraction : offer.getAttractions()) {
                attraction.setOffer(null);
            }
        }
        offerRepository.deleteById(id);
    }

    public boolean existsOfferById(Long id) {
        return offerRepository.existsById(id);
    }

    private void setHotelById(Offer offer, Long id) {
        var hotel = hotelService.getHotelById(id);
        offer.setHotel(hotel);
    }

    private void setTransportById(Offer offer, Long id) {
        var transport = transportService.getTransportById(id);
        offer.setTransport(transport);
    }
}