package pl.pelikan.pelikanbe.photo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hotel.Hotel;
import pl.pelikan.pelikanbe.hotel.HotelService;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttraction;
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
        setHotel(photo);
        setAttraction(photo);
        return repository.save(photo);
    }

    public Photo updatePhoto(Photo photo) {
        setHotel(photo);
        setAttraction(photo);
        return repository.save(photo);
    }

    public void deletePhoto(Long id) {
        repository.deleteById(id);
    }

    public boolean existsPhotoById(Long id) {
        return repository.existsById(id);
    }

    private void setHotel(Photo photo) {
        Hotel initHotel = photo.getHotel();
        if (initHotel != null && initHotel.getId() != null) {
            Hotel hotel = hotelService.getHotelById(initHotel.getId());
            photo.setHotel(hotel);
        }
    }

    private void setAttraction(Photo photo) {
        TouristAttraction initAttraction = photo.getTouristAttraction();
        if (initAttraction != null && initAttraction.getId() != null) {
            TouristAttraction attraction = attractionService.getAttractionById(initAttraction.getId());
            photo.setTouristAttraction(attraction);
        }
    }
}
