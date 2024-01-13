package pl.pelikan.pelikanbe.tourist_attraction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.offer.Offer;
import pl.pelikan.pelikanbe.offer.OfferService;
import pl.pelikan.pelikanbe.photo.Photo;

import java.util.List;

@Service
@AllArgsConstructor
public class TouristAttractionService {

    private final TouristAttractionRepository touristAttractionRepository;

    private final OfferService offerService;

    public TouristAttraction getTouristAttractionById(Long id) {
        return touristAttractionRepository.findById(id).orElse(null);
    }

    public List<TouristAttraction> getTouristAttractions() {
        return touristAttractionRepository.findAll();
    }

    public TouristAttraction addTouristAttraction(TouristAttraction attraction) {
        Offer offer = attraction.getOffer();
        if (offer != null && offer.getId() != null) {
            setOfferById(attraction, offer.getId());
        }
        return touristAttractionRepository.save(attraction);
    }

    public TouristAttraction updateTouristAttraction(TouristAttraction attraction) {
        Offer offer = attraction.getOffer();
        if (offer != null && offer.getId() != null) {
            setOfferById(attraction, offer.getId());
        }
        return touristAttractionRepository.save(attraction);
    }

    public void deleteTouristAttraction(Long id) {
        TouristAttraction attraction = getTouristAttractionById(id);
        if (attraction != null) {
            if (attraction.getPhotos() != null) {
                for (Photo photo : attraction.getPhotos()) {
                    photo.setTouristAttraction(null);
                }
            }
            touristAttractionRepository.deleteById(id);
        }
    }

    public boolean existsTouristAttractionById(Long id) {
        return touristAttractionRepository.existsById(id);
    }

    private void setOfferById(TouristAttraction attraction, Long id) {
        Offer offer = offerService.getOfferById(id);
        attraction.setOffer(offer);
    }
}