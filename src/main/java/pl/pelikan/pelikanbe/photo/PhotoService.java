package pl.pelikan.pelikanbe.photo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hotel.HotelService;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttractionService;

import java.util.List;

@Service
@AllArgsConstructor
public class PhotoService {

    private final PhotoRepository repository;

    private final HotelService hotelService;

    private final TouristAttractionService attractionService;

    public Photo getPhotoById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Photo> getPhotos() {
        return repository.findAll();
    }

    public Photo addPhoto(Photo photo) {
        var hotel = photo.getHotel();
        if (hotel != null && hotel.getId() != null) {
            setHotelById(photo, hotel.getId());
        }
        var attraction = photo.getTouristAttraction();
        if (attraction != null && attraction.getId() != null) {
            setAttractionById(photo, attraction.getId());
        }
        return repository.save(photo);
    }

    public Photo updatePhoto(Photo photo) {
        var hotel = photo.getHotel();
        if (hotel != null && hotel.getId() != null) {
            setHotelById(photo, hotel.getId());
        }
        var attraction = photo.getTouristAttraction();
        if (attraction != null && attraction.getId() != null) {
            setAttractionById(photo, attraction.getId());
        }
        return repository.save(photo);
    }

    public void deletePhoto(Long id) {
        repository.deleteById(id);
    }

    public boolean existsPhotoById(Long id) {
        return repository.existsById(id);
    }

    private void setHotelById(Photo photo, Long id) {
        var hotel = hotelService.getHotelById(id);
        photo.setHotel(hotel);
    }

    private void setAttractionById(Photo photo, Long id) {
        var attraction = attractionService.getAttractionById(id);
        photo.setTouristAttraction(attraction);
    }
}
