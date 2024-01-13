package pl.pelikan.pelikanbe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pelikan.pelikanbe.offer.OfferService;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttraction;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttractionRepository;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttractionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TouristAttractionServiceTest {

    private TouristAttractionRepository touristAttractionRepository;

    private TouristAttractionService touristAttractionService;

    @BeforeEach
    public void setUp() {
        touristAttractionRepository = Mockito.mock(TouristAttractionRepository.class);
        OfferService offerService = Mockito.mock(OfferService.class);
        touristAttractionService = new TouristAttractionService(touristAttractionRepository, offerService);
    }

    @Test
    public void testGetTouristAttractionById() {
        //GIVEN
        Long touristAttractionId = 1L;
        TouristAttraction touristAttraction = createTouristAttractionWithGivenId(1L);
        when(touristAttractionRepository.findById(touristAttractionId)).thenReturn(Optional.of(touristAttraction));

        //WHEN
        TouristAttraction retrievedTouristAttraction = touristAttractionService.getTouristAttractionById(touristAttractionId);

        //THEN
        assertNotNull(retrievedTouristAttraction);
        assertEquals(1L, retrievedTouristAttraction.getId());
        assertEquals("AquaPark", retrievedTouristAttraction.getName());
        assertEquals(BigDecimal.valueOf(49), retrievedTouristAttraction.getPrice());
        assertEquals("Awesome fun", retrievedTouristAttraction.getDescription());
        verify(touristAttractionRepository, times(1)).findById(touristAttractionId);
    }

    @Test
    public void testGetTouristAttractions() {
        //GIVEN
        List<TouristAttraction> touristAttractions = new ArrayList<>();
        TouristAttraction touristAttraction1 = createTouristAttractionWithGivenId(1L);
        TouristAttraction touristAttraction2 = createTouristAttractionWithGivenId(2L);
        touristAttractions.add(touristAttraction1);
        touristAttractions.add(touristAttraction2);
        when(touristAttractionRepository.findAll()).thenReturn(touristAttractions);

        //WHEN
        List<TouristAttraction> retrievedTouristAttractions = touristAttractionService.getTouristAttractions();

        //THEN
        assertNotNull(retrievedTouristAttractions);
        assertEquals(2, retrievedTouristAttractions.size());
        verify(touristAttractionRepository, times(1)).findAll();
    }

    @Test
    public void testAddTouristAttraction() {
        //GIVEN
        TouristAttraction touristAttraction = createTouristAttractionWithGivenId(2L);
        when(touristAttractionRepository.save(touristAttraction)).thenReturn(touristAttraction);

        //WHEN
        TouristAttraction createdTouristAttraction = touristAttractionService.addTouristAttraction(touristAttraction);

        //THEN
        assertNotNull(createdTouristAttraction);
        assertEquals(2L, createdTouristAttraction.getId());
        assertEquals("AquaPark", createdTouristAttraction.getName());
        assertEquals(BigDecimal.valueOf(49), createdTouristAttraction.getPrice());
        assertEquals("Awesome fun", createdTouristAttraction.getDescription());
        verify(touristAttractionRepository, times(1)).save(touristAttraction);
    }

    @Test
    public void testUpdateTouristAttractionCounter() {
        //GIVEN
        TouristAttraction touristAttraction = createTouristAttractionWithGivenId(3L);
        when(touristAttractionRepository.save(touristAttraction)).thenReturn(touristAttraction);

        //WHEN
        TouristAttraction updatedTouristAttraction = touristAttractionService.updateTouristAttraction(touristAttraction);

        //THEN
        assertNotNull(updatedTouristAttraction);
        assertEquals(3L, updatedTouristAttraction.getId());
        assertEquals("AquaPark", updatedTouristAttraction.getName());
        assertEquals(BigDecimal.valueOf(49), updatedTouristAttraction.getPrice());
        assertEquals("Awesome fun", updatedTouristAttraction.getDescription());
        verify(touristAttractionRepository, times(1)).save(touristAttraction);
    }

    @Test
    public void testDeleteTouristAttraction() {
        //GIVEN
        Long touristAttractionId = 1L;
        TouristAttraction touristAttraction = createTouristAttractionWithGivenId(touristAttractionId);
        when(touristAttractionRepository.findById(touristAttractionId)).thenReturn(Optional.of(touristAttraction));

        //WHEN
        touristAttractionService.deleteTouristAttraction(touristAttractionId);

        //THEN
        verify(touristAttractionRepository, times(1)).deleteById(touristAttractionId);
    }

    @Test
    public void testDeleteTouristAttractionWhenTouristAttractionNotExists() {
        //GIVEN
        Long touristAttractionId = 1L;
        when(touristAttractionRepository.existsById(touristAttractionId)).thenReturn(false);

        //WHEN
        touristAttractionService.deleteTouristAttraction(touristAttractionId);

        //THEN
        verify(touristAttractionRepository, never()).deleteById(anyLong());
    }

    private TouristAttraction createTouristAttractionWithGivenId(Long id) {
        TouristAttraction touristAttraction = new TouristAttraction();
        touristAttraction.setId(id);
        touristAttraction.setName("AquaPark");
        touristAttraction.setPrice(BigDecimal.valueOf(49));
        touristAttraction.setDescription("Awesome fun");
        return touristAttraction;
    }
}
