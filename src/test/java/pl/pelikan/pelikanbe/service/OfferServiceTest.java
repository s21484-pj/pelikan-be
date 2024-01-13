package pl.pelikan.pelikanbe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pelikan.pelikanbe.hotel.HotelService;
import pl.pelikan.pelikanbe.offer.Offer;
import pl.pelikan.pelikanbe.offer.OfferRepository;
import pl.pelikan.pelikanbe.offer.OfferService;
import pl.pelikan.pelikanbe.transport.TransportService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class OfferServiceTest {

    private OfferRepository offerRepository;

    private OfferService offerService;

    @BeforeEach
    public void setUp() {
        offerRepository = Mockito.mock(OfferRepository.class);
        HotelService hotelService = Mockito.mock(HotelService.class);
        TransportService transportService = Mockito.mock(TransportService.class);
        offerService = new OfferService(offerRepository, hotelService, transportService);
    }

    @Test
    public void testGetOfferById() {
        //GIVEN
        Long offerId = 1L;
        Offer offer = createOfferWithGivenId(1L);
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        //WHEN
        Offer retrievedOffer = offerService.getOfferById(offerId);

        //THEN
        assertNotNull(retrievedOffer);
        assertEquals(1L, retrievedOffer.getId());
        assertEquals("Wczasy pod grusza", retrievedOffer.getName());
        assertEquals("Poland", retrievedOffer.getLocation());
        assertEquals(LocalDate.of(2024, 1, 13), retrievedOffer.getTermFrom());
        assertEquals(BigDecimal.valueOf(1999), retrievedOffer.getPrice());
        assertEquals("Great views", retrievedOffer.getDescription());
        assertEquals(44, retrievedOffer.getQuantity());
        verify(offerRepository, times(1)).findById(offerId);
    }

    @Test
    public void testGetOffers() {
        //GIVEN
        List<Offer> offers = new ArrayList<>();
        Offer offer1 = createOfferWithGivenId(1L);
        Offer offer2 = createOfferWithGivenId(2L);
        offers.add(offer1);
        offers.add(offer2);
        when(offerRepository.findAll()).thenReturn(offers);

        //WHEN
        List<Offer> retrievedOffers = offerService.getOffers();

        //THEN
        assertNotNull(retrievedOffers);
        assertEquals(2, retrievedOffers.size());
        verify(offerRepository, times(1)).findAll();
    }

    @Test
    public void testAddOffer() {
        //GIVEN
        Offer offer = createOfferWithGivenId(2L);
        when(offerRepository.save(offer)).thenReturn(offer);

        //WHEN
        Offer createdOffer = offerService.addOffer(offer);

        //THEN
        assertNotNull(createdOffer);
        assertEquals(2L, createdOffer.getId());
        assertEquals("Wczasy pod grusza", createdOffer.getName());
        assertEquals("Poland", createdOffer.getLocation());
        assertEquals(LocalDate.of(2024, 1, 13), createdOffer.getTermFrom());
        assertEquals(BigDecimal.valueOf(1999), createdOffer.getPrice());
        assertEquals("Great views", createdOffer.getDescription());
        assertEquals(44, createdOffer.getQuantity());
        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    public void testUpdateOfferCounter() {
        //GIVEN
        Offer offer = createOfferWithGivenId(3L);
        when(offerRepository.save(offer)).thenReturn(offer);

        //WHEN
        Offer updatedOffer = offerService.updateOffer(offer);

        //THEN
        assertNotNull(updatedOffer);
        assertEquals(3L, updatedOffer.getId());
        assertEquals("Wczasy pod grusza", updatedOffer.getName());
        assertEquals("Poland", updatedOffer.getLocation());
        assertEquals(LocalDate.of(2024, 1, 13), updatedOffer.getTermFrom());
        assertEquals(BigDecimal.valueOf(1999), updatedOffer.getPrice());
        assertEquals("Great views", updatedOffer.getDescription());
        assertEquals(44, updatedOffer.getQuantity());
        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    public void testDeleteOffer() {
        //GIVEN
        Long offerId = 1L;
        Offer offer = createOfferWithGivenId(offerId);
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        //WHEN
        offerService.deleteOffer(offerId);

        //THEN
        verify(offerRepository, times(1)).deleteById(offerId);
    }

    @Test
    public void testDeleteOfferWhenOfferNotExists() {
        //GIVEN
        Long offerId = 1L;
        when(offerRepository.existsById(offerId)).thenReturn(false);

        //WHEN
        offerService.deleteOffer(offerId);

        //THEN
        verify(offerRepository, never()).deleteById(anyLong());
    }

    private Offer createOfferWithGivenId(Long id) {
        Offer offer = new Offer();
        offer.setId(id);
        offer.setName("Wczasy pod grusza");
        offer.setLocation("Poland");
        offer.setTermFrom(LocalDate.of(2024, 1, 13));
        offer.setTermTo(LocalDate.of(2024, 1, 22));
        offer.setPrice(BigDecimal.valueOf(1999));
        offer.setDescription("Great views");
        offer.setQuantity(44);
        return offer;
    }
}
