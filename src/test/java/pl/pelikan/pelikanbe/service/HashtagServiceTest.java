package pl.pelikan.pelikanbe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.hashtag.HashtagRepository;
import pl.pelikan.pelikanbe.hashtag.HashtagService;
import pl.pelikan.pelikanbe.offer.OfferService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class HashtagServiceTest {

    private HashtagRepository hashtagRepository;

    private HashtagService hashtagService;

    @BeforeEach
    public void setUp() {
        hashtagRepository = Mockito.mock(HashtagRepository.class);
        OfferService offerService = Mockito.mock(OfferService.class);
        hashtagService = new HashtagService(hashtagRepository, offerService);
    }

    @Test
    public void testGetHashtagById() {
        //GIVEN
        Long hashtagId = 1L;
        Hashtag hashtag = createHashtagWithGivenId(1L);
        when(hashtagRepository.findById(hashtagId)).thenReturn(Optional.of(hashtag));

        //WHEN
        Hashtag retrievedHashtag = hashtagService.getHashtagById(hashtagId);

        //THEN
        assertNotNull(retrievedHashtag);
        assertEquals(1L, retrievedHashtag.getId());
        assertEquals("#Canada", retrievedHashtag.getName());
        verify(hashtagRepository, times(1)).findById(hashtagId);
    }

    @Test
    public void testGetHashtags() {
        //GIVEN
        List<Hashtag> hashtags = new ArrayList<>();
        Hashtag hashtag1 = createHashtagWithGivenId(1L);
        Hashtag hashtag2 = createHashtagWithGivenId(2L);
        hashtags.add(hashtag1);
        hashtags.add(hashtag2);
        when(hashtagRepository.findAll()).thenReturn(hashtags);

        //WHEN
        List<Hashtag> retrievedHashtags = hashtagService.getHashtags();

        //THEN
        assertNotNull(retrievedHashtags);
        assertEquals(2, retrievedHashtags.size());
        verify(hashtagRepository, times(1)).findAll();
    }

    @Test
    public void testAddHashtag() {
        //GIVEN
        Hashtag hashtag = createHashtagWithGivenId(1L);
        when(hashtagRepository.save(hashtag)).thenReturn(hashtag);

        //WHEN
        Hashtag createdHashtag = hashtagService.addHashtag(hashtag);

        //THEN
        assertNotNull(createdHashtag);
        assertEquals(1L, createdHashtag.getId());
        assertEquals("#Canada", createdHashtag.getName());
        verify(hashtagRepository, times(1)).save(hashtag);
    }

    @Test
    public void testUpdateHashtag() {
        //GIVEN
        Hashtag hashtag = createHashtagWithGivenId(2L);
        when(hashtagRepository.save(hashtag)).thenReturn(hashtag);

        //WHEN
        Hashtag updatedHashtag = hashtagService.updateHashtag(hashtag);

        //THEN
        assertNotNull(updatedHashtag);
        assertEquals(2L, updatedHashtag.getId());
        assertEquals("#Canada", updatedHashtag.getName());
        verify(hashtagRepository, times(1)).save(hashtag);
    }

    @Test
    public void testDeleteHashtag() {
        //GIVEN
        Long hashtagId = 1L;
        Hashtag hashtag = createHashtagWithGivenId(hashtagId);
        when(hashtagRepository.findById(hashtagId)).thenReturn(Optional.of(hashtag));

        //WHEN
        hashtagService.deleteHashtag(hashtagId);

        //THEN
        verify(hashtagRepository, times(1)).deleteById(hashtagId);
    }

    @Test
    public void testDeleteHashtagWhenHashtagNotExists() {
        //GIVEN
        Long hashtagId = 1L;
        when(hashtagRepository.existsById(hashtagId)).thenReturn(false);

        //WHEN
        hashtagService.deleteHashtag(hashtagId);

        //THEN
        verify(hashtagRepository, never()).deleteById(anyLong());
    }

    private Hashtag createHashtagWithGivenId(Long id) {
        Hashtag hashtag = new Hashtag();
        hashtag.setId(id);
        hashtag.setName("#Canada");
        return hashtag;
    }
}
