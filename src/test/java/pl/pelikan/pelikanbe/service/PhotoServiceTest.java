package pl.pelikan.pelikanbe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pelikan.pelikanbe.hotel.HotelService;
import pl.pelikan.pelikanbe.photo.Photo;
import pl.pelikan.pelikanbe.photo.PhotoRepository;
import pl.pelikan.pelikanbe.photo.PhotoService;
import pl.pelikan.pelikanbe.tourist_attraction.TouristAttractionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PhotoServiceTest {

    private PhotoRepository photoRepository;

    private PhotoService photoService;

    @BeforeEach
    public void setUp() {
        photoRepository = Mockito.mock(PhotoRepository.class);
        HotelService hotelService = Mockito.mock(HotelService.class);
        TouristAttractionService touristAttractionService = Mockito.mock(TouristAttractionService.class);
        photoService = new PhotoService(photoRepository, hotelService, touristAttractionService);
    }

    @Test
    public void testGetPhotoById() {
        //GIVEN
        Long photoId = 1L;
        Photo photo = createPhotoWithGivenId(1L);
        when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));

        //WHEN
        Photo retrievedPhoto = photoService.getPhotoById(photoId);

        //THEN
        assertNotNull(retrievedPhoto);
        assertEquals(1L, retrievedPhoto.getId());
        assertEquals("https://example.com", retrievedPhoto.getUrl());
        verify(photoRepository, times(1)).findById(photoId);
    }

    @Test
    public void testGetPhotos() {
        //GIVEN
        List<Photo> photos = new ArrayList<>();
        Photo photo1 = createPhotoWithGivenId(1L);
        Photo photo2 = createPhotoWithGivenId(2L);
        photos.add(photo1);
        photos.add(photo2);
        when(photoRepository.findAll()).thenReturn(photos);

        //WHEN
        List<Photo> retrievedPhotos = photoService.getPhotos();

        //THEN
        assertNotNull(retrievedPhotos);
        assertEquals(2, retrievedPhotos.size());
        verify(photoRepository, times(1)).findAll();
    }

    @Test
    public void testAddPhoto() {
        //GIVEN
        Photo photo = createPhotoWithGivenId(2L);
        when(photoRepository.save(photo)).thenReturn(photo);

        //WHEN
        Photo createdPhoto = photoService.addPhoto(photo);

        //THEN
        assertNotNull(createdPhoto);
        assertEquals(2L, createdPhoto.getId());
        assertEquals("https://example.com", createdPhoto.getUrl());
        verify(photoRepository, times(1)).save(photo);
    }

    @Test
    public void testUpdatePhotoCounter() {
        //GIVEN
        Photo photo = createPhotoWithGivenId(3L);
        when(photoRepository.save(photo)).thenReturn(photo);

        //WHEN
        Photo updatedPhoto = photoService.updatePhoto(photo);

        //THEN
        assertNotNull(updatedPhoto);
        assertEquals(3L, updatedPhoto.getId());
        assertEquals("https://example.com", updatedPhoto.getUrl());
        verify(photoRepository, times(1)).save(photo);
    }

    @Test
    public void testDeletePhoto() {
        //GIVEN
        Long photoId = 1L;
        when(photoRepository.existsById(photoId)).thenReturn(true);

        //WHEN
        photoService.deletePhoto(photoId);

        //THEN
        verify(photoRepository, times(1)).deleteById(photoId);
    }

    @Test
    public void testDeletePhotoWhenPhotoNotExists() {
        //GIVEN
        Long photoId = 1L;
        when(photoRepository.existsById(photoId)).thenReturn(false);

        //WHEN
        photoService.deletePhoto(photoId);

        //THEN
        verify(photoRepository, never()).deleteById(anyLong());
    }

    private Photo createPhotoWithGivenId(Long id) {
        Photo photo = new Photo();
        photo.setId(id);
        photo.setUrl("https://example.com");
        return photo;
    }
}
