package pl.pelikan.pelikanbe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pelikan.pelikanbe.transport.Transport;
import pl.pelikan.pelikanbe.transport.TransportRepository;
import pl.pelikan.pelikanbe.transport.TransportService;
import pl.pelikan.pelikanbe.transport.TransportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TransportServiceTest {

    private TransportRepository transportRepository;

    private TransportService transportService;

    @BeforeEach
    public void setUp() {
        transportRepository = Mockito.mock(TransportRepository.class);
        transportService = new TransportService(transportRepository);
    }

    @Test
    public void testGetTransportById() {
        //GIVEN
        Long transportId = 1L;
        Transport transport = createTransportWithGivenId(1L);
        when(transportRepository.findById(transportId)).thenReturn(Optional.of(transport));

        //WHEN
        Transport retrievedTransport = transportService.getTransportById(transportId);

        //THEN
        assertNotNull(retrievedTransport);
        assertEquals(1L, retrievedTransport.getId());
        assertEquals(TransportType.PLANE, retrievedTransport.getTransportType());
        assertEquals("Air Canada", retrievedTransport.getName());
        assertEquals("Tani lot", retrievedTransport.getDescription());
        verify(transportRepository, times(1)).findById(transportId);
    }

    @Test
    public void testGetTransports() {
        //GIVEN
        List<Transport> transports = new ArrayList<>();
        Transport transport1 = createTransportWithGivenId(1L);
        Transport transport2 = createTransportWithGivenId(2L);
        transports.add(transport1);
        transports.add(transport2);
        when(transportRepository.findAll()).thenReturn(transports);

        //WHEN
        List<Transport> retrievedTransports = transportService.getTransports();

        //THEN
        assertNotNull(retrievedTransports);
        assertEquals(2, retrievedTransports.size());
        verify(transportRepository, times(1)).findAll();
    }

    @Test
    public void testAddTransport() {
        //GIVEN
        Transport transport = createTransportWithGivenId(2L);
        when(transportRepository.save(transport)).thenReturn(transport);

        //WHEN
        Transport createdTransport = transportService.addTransport(transport);

        //THEN
        assertNotNull(createdTransport);
        assertEquals(2L, createdTransport.getId());
        assertEquals(TransportType.PLANE, createdTransport.getTransportType());
        assertEquals("Air Canada", createdTransport.getName());
        assertEquals("Tani lot", createdTransport.getDescription());
        verify(transportRepository, times(1)).save(transport);
    }

    @Test
    public void testUpdateTransport() {
        //GIVEN
        Transport transport = createTransportWithGivenId(3L);
        when(transportRepository.save(transport)).thenReturn(transport);

        //WHEN
        Transport createdTransport = transportService.updateTransport(transport);

        //THEN
        assertNotNull(createdTransport);
        assertEquals(3L, createdTransport.getId());
        assertEquals(TransportType.PLANE, createdTransport.getTransportType());
        assertEquals("Air Canada", createdTransport.getName());
        assertEquals("Tani lot", createdTransport.getDescription());
        verify(transportRepository, times(1)).save(transport);
    }

    @Test
    public void testDeleteTransport() {
        //GIVEN
        Long transportId = 1L;
        Transport transport = createTransportWithGivenId(transportId);
        when(transportRepository.findById(transportId)).thenReturn(Optional.of(transport));

        //WHEN
        transportService.deleteTransport(transportId);

        //THEN
        verify(transportRepository, times(1)).deleteById(transportId);
    }

    @Test
    public void testDeleteTransportWhenTransportNotExists() {
        //GIVEN
        Long transportId = 1L;
        when(transportRepository.existsById(transportId)).thenReturn(false);

        //WHEN
        transportService.deleteTransport(transportId);

        //THEN
        verify(transportRepository, never()).deleteById(anyLong());
    }

    private Transport createTransportWithGivenId(Long id) {
        Transport transport = new Transport();
        transport.setId(id);
        transport.setTransportType(TransportType.PLANE);
        transport.setName("Air Canada");
        transport.setDescription("Tani lot");
        return transport;
    }
}
