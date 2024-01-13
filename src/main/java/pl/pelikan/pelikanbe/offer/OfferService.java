package pl.pelikan.pelikanbe.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.hotel.Hotel;
import pl.pelikan.pelikanbe.hotel.HotelService;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttraction;
import pl.pelikan.pelikanbe.transport.Transport;
import pl.pelikan.pelikanbe.transport.TransportService;
import pl.pelikan.pelikanbe.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
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
        setHotel(offer);
        setTransport(offer);
        return offerRepository.save(offer);
    }

    public Offer updateOffer(Offer offer) {
        setHotel(offer);
        setTransport(offer);
        return offerRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        Offer offer = getOfferById(id);
        if (offer != null) {
            if (offer.getHotel() != null) {
                offer.setHotel(null);
            }
            if (offer.getTransport() != null) {
                offer.setTransport(null);
            }
            if (offer.getAttractions() != null) {
                for (TouristAttraction attraction : offer.getAttractions()) {
                    attraction.setOffer(null);
                }
            }
            if (offer.getHashtags() != null) {
                for (Hashtag hashtag : offer.getHashtags()) {
                    List<Offer> offers = hashtag.getOffers();
                    offers.remove(offer);
                    hashtag.setOffers(offers);
                }
            }
            if (offer.getUsers() != null) {
                for (User user : offer.getUsers()) {
                    List<Offer> offers = user.getOffers();
                    offers.remove(offer);
                    user.setOffers(offers);
                }
            }
            offerRepository.deleteById(id);
        }
    }

    public boolean existsOfferById(Long id) {
        return offerRepository.existsById(id);
    }

    private void setHotel(Offer offer) {
        Hotel initHotel = offer.getHotel();
        if (initHotel != null && initHotel.getId() != null) {
            Hotel hotel = hotelService.getHotelById(initHotel.getId());
            offer.setHotel(hotel);
        }
    }

    private void setTransport(Offer offer) {
        Transport initTransport = offer.getTransport();
        if (initTransport != null && initTransport.getId() != null) {
            Transport transport = transportService.getTransportById(initTransport.getId());
            offer.setTransport(transport);
        }
    }
}