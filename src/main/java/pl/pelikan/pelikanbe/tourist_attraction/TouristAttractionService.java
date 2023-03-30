package pl.pelikan.pelikanbe.tourist_attraction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.offer.OfferService;

import java.util.List;

@Service
@AllArgsConstructor
public class TouristAttractionService {

    private final TouristAttractionRepository repository;

    private final OfferService offerService;

    public TouristAttraction getAttractionById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<TouristAttraction> getAttractions() {
        return repository.findAll();
    }

    public TouristAttraction addAttraction(TouristAttraction attraction) {
        var offer = attraction.getOffer();
        if (offer != null && offer.getId() != null) {
            setOfferById(attraction, offer.getId());
        }
        return repository.save(attraction);
    }

    public TouristAttraction updateAttraction(TouristAttraction attraction) {
        var offer = attraction.getOffer();
        if (offer != null && offer.getId() != null) {
            setOfferById(attraction, offer.getId());
        }
        return repository.save(attraction);
    }

    public void deleteAttraction(Long id) {
        var attraction = getAttractionById(id);
        if (attraction.getPhotos() != null) {
            for (var photo : attraction.getPhotos()) {
                photo.setTouristAttraction(null);
            }
        }
        repository.deleteById(id);
    }

    public boolean existsTouristAttractionById(Long id) {
        return repository.existsById(id);
    }

    private void setOfferById(TouristAttraction attraction, Long id) {
        var offer = offerService.getOfferById(id);
        attraction.setOffer(offer);
    }
}