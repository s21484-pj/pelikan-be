package pl.pelikan.pelikanbe.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.hashtag.HashtagService;
import pl.pelikan.pelikanbe.hotel.HotelService;
import pl.pelikan.pelikanbe.transport.TransportService;
import pl.pelikan.pelikanbe.user.User;
import pl.pelikan.pelikanbe.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    private final HotelService hotelService;

    private final TransportService transportService;

    private final HashtagService hashtagService;

    private final UserService userService;

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
        if (offer.getHashtags() != null) {
            setHashtags(offer);
        }
        if (offer.getUsers() != null) {
            setUsers(offer);
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
        if (offer.getHashtags() != null) {
            setHashtags(offer);
        }
        if (offer.getUsers() != null) {
            setUsers(offer);
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
        if (offer.getHashtags() != null) {
            for (var hashtag : offer.getHashtags()) {
                hashtag.setOffers(null);
            }
        }
        if (offer.getUsers() != null) {
            for (var user : offer.getUsers()) {
                user.setOffers(null);
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

    private void setHashtags(Offer offer) {
        List<Hashtag> hashtags = new ArrayList<>();
        for (Hashtag tempHashtag : offer.getHashtags()) {
            if (tempHashtag.getId() != null) {
                Hashtag hashtag = hashtagService.getHashtagById(tempHashtag.getId());
                hashtags.add(hashtag);
            }
        }
        offer.setHashtags(hashtags);
    }

    private void setUsers(Offer offer) {
        List<User> users = new ArrayList<>();
        for (User tempUser : offer.getUsers()) {
            if (tempUser.getId() != null) {
                User user = userService.getUserById(tempUser.getId());
                users.add(user);
            }
        }
        offer.setUsers(users);
    }
}