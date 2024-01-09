package pl.pelikan.pelikanbe.hashtag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounter;
import pl.pelikan.pelikanbe.offer.Offer;
import pl.pelikan.pelikanbe.offer.OfferService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    private final OfferService offerService;

    public Hashtag getHashtagById(Long id) {
        return hashtagRepository.findById(id).orElse(null);
    }

    public Hashtag getHashtagByName(String name) {
        return hashtagRepository.findByName(name);
    }

    public List<Hashtag> getHashtags() {
        return hashtagRepository.findAll();
    }

    public Hashtag addHashtag(Hashtag hashtag) {
        if (hashtag.getOffers() != null) {
            setOffers(hashtag);
        }
        return hashtagRepository.save(hashtag);
    }

    public Hashtag updateHashtag(Hashtag hashtag) {
        if (hashtag.getOffers() != null) {
            setOffers(hashtag);
        }
        return hashtagRepository.save(hashtag);
    }

    public void deleteHashtag(Long id) {
        Hashtag hashtag = getHashtagById(id);
        if (hashtag.getHashtagCounters() != null) {
            for (HashtagCounter counter : hashtag.getHashtagCounters()) {
                counter.setHashtag(null);
            }
        }
        if (hashtag.getOffers() != null) {
            for (Offer offer : hashtag.getOffers()) {
                List<Hashtag> hashtags = offer.getHashtags();
                hashtags.remove(hashtag);
                offer.setHashtags(hashtags);
            }
        }
        hashtagRepository.deleteById(id);
    }

    public boolean existsHashtagById(Long id) {
        return hashtagRepository.existsById(id);
    }

    private void setOffers(Hashtag hashtag) {
        List<Offer> offers = new ArrayList<>();
        for (Offer initOffer : hashtag.getOffers()) {
            if (initOffer.getId() != null) {
                Offer offer = offerService.getOfferById(initOffer.getId());
                offers.add(offer);
            }
        }
        hashtag.setOffers(offers);
    }
}
