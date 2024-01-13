package pl.pelikan.pelikanbe.hotel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.offer.Offer;
import pl.pelikan.pelikanbe.photo.Photo;

import java.util.List;

@Service
@AllArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    public Hotel addHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        Hotel hotel = getHotelById(id);
        if (hotel != null) {
            if (hotel.getOffer() != null) {
                Offer offer = hotel.getOffer();
                offer.setHotel(null);
            }
            if (hotel.getPhotos() != null) {
                for (Photo photo : hotel.getPhotos()) {
                    photo.setHotel(null);
                }
            }
            hotelRepository.deleteById(id);
        }
    }

    public boolean existsHotelById(Long id) {
        return hotelRepository.existsById(id);
    }
}