package pl.pelikan.pelikanbe.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pelikan.pelikanbe.hotel.Hotel;
import pl.pelikan.pelikanbe.hotel.HotelRepository;
import pl.pelikan.pelikanbe.hotel.HotelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class HotelServiceTest {

    private HotelRepository hotelRepository;

    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        hotelRepository = Mockito.mock(HotelRepository.class);
        hotelService = new HotelService(hotelRepository);
    }

    @Test
    public void testGetHotelById() {
        //GIVEN
        Long hotelId = 1L;
        Hotel hotel = createHotelWithGivenId(1L);
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        //WHEN
        Hotel retrievedHotel = hotelService.getHotelById(hotelId);

        //THEN
        assertNotNull(retrievedHotel);
        assertEquals(1L, retrievedHotel.getId());
        assertEquals("Mercure Gdansk Stare Miasto", retrievedHotel.getName());
        assertEquals("Jana Heweliusza 22", retrievedHotel.getAddress());
        assertEquals(4.5F, retrievedHotel.getStars());
        assertEquals("Nowoczesny hotel", retrievedHotel.getDescription());
        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    public void testGetHotels() {
        //GIVEN
        List<Hotel> hotels = new ArrayList<>();
        Hotel hotel1 = createHotelWithGivenId(1L);
        Hotel hotel2 = createHotelWithGivenId(2L);
        hotels.add(hotel1);
        hotels.add(hotel2);
        when(hotelRepository.findAll()).thenReturn(hotels);

        //WHEN
        List<Hotel> retrievedHotels = hotelService.getHotels();

        //THEN
        assertNotNull(retrievedHotels);
        assertEquals(2, retrievedHotels.size());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    public void testAddHotel() {
        //GIVEN
        Hotel hotel = createHotelWithGivenId(1L);
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        //WHEN
        Hotel createdHotel = hotelService.addHotel(hotel);

        //THEN
        assertNotNull(createdHotel);
        assertEquals(1L, createdHotel.getId());
        assertEquals("Mercure Gdansk Stare Miasto", createdHotel.getName());
        assertEquals("Jana Heweliusza 22", createdHotel.getAddress());
        assertEquals(4.5F, createdHotel.getStars());
        assertEquals("Nowoczesny hotel", createdHotel.getDescription());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    public void testUpdateHotel() {
        //GIVEN
        Hotel hotel = createHotelWithGivenId(2L);
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        //WHEN
        Hotel updatedHotel = hotelService.updateHotel(hotel);

        //THEN
        assertNotNull(updatedHotel);
        assertEquals(2L, updatedHotel.getId());
        assertEquals("Mercure Gdansk Stare Miasto", updatedHotel.getName());
        assertEquals("Jana Heweliusza 22", updatedHotel.getAddress());
        assertEquals(4.5F, updatedHotel.getStars());
        assertEquals("Nowoczesny hotel", updatedHotel.getDescription());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    public void testDeleteHotel() {
        //GIVEN
        Long hotelId = 1L;
        Hotel hotel = createHotelWithGivenId(hotelId);
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        //WHEN
        hotelService.deleteHotel(hotelId);

        //THEN
        verify(hotelRepository, times(1)).deleteById(hotelId);
    }

    @Test
    public void testDeleteHotelWhenHotelNotExists() {
        //GIVEN
        Long hotelId = 1L;
        when(hotelRepository.existsById(hotelId)).thenReturn(false);

        //WHEN
        hotelService.deleteHotel(hotelId);

        //THEN
        verify(hotelRepository, never()).deleteById(anyLong());
    }

    private Hotel createHotelWithGivenId(Long id) {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName("Mercure Gdansk Stare Miasto");
        hotel.setAddress("Jana Heweliusza 22");
        hotel.setStars(4.5F);
        hotel.setDescription("Nowoczesny hotel");
        return hotel;
    }
}
