package pl.pelikan.pelikanbe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pelikan.pelikanbe.hashtag.HashtagService;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounter;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounterRepository;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounterService;
import pl.pelikan.pelikanbe.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class HashtagCounterServiceTest {

    private HashtagCounterRepository hashtagCounterRepository;

    private HashtagCounterService hashtagCounterService;

    @BeforeEach
    public void setUp() {
        hashtagCounterRepository = Mockito.mock(HashtagCounterRepository.class);
        UserService userService = Mockito.mock(UserService.class);
        HashtagService hashtagService = Mockito.mock(HashtagService.class);
        hashtagCounterService = new HashtagCounterService(hashtagCounterRepository, userService, hashtagService);
    }

    @Test
    public void testGetHashtagById() {
        //GIVEN
        Long hashtagCounterId = 1L;
        HashtagCounter hashtagCounter = createHashtagCounterWithGivenId(1L);
        when(hashtagCounterRepository.findById(hashtagCounterId)).thenReturn(Optional.of(hashtagCounter));

        //WHEN
        HashtagCounter retrievedHashtag = hashtagCounterService.getHashtagCounterById(hashtagCounterId);

        //THEN
        assertNotNull(retrievedHashtag);
        assertEquals(1L, retrievedHashtag.getId());
        assertEquals(12, retrievedHashtag.getCount());
        verify(hashtagCounterRepository, times(1)).findById(hashtagCounterId);
    }

    @Test
    public void testGetHashtags() {
        //GIVEN
        List<HashtagCounter> hashtagCounters = new ArrayList<>();
        HashtagCounter hashtagCounter1 = createHashtagCounterWithGivenId(1L);
        HashtagCounter hashtagCounter2 = createHashtagCounterWithGivenId(2L);
        hashtagCounters.add(hashtagCounter1);
        hashtagCounters.add(hashtagCounter2);
        when(hashtagCounterRepository.findAll()).thenReturn(hashtagCounters);

        //WHEN
        List<HashtagCounter> retrievedHashtags = hashtagCounterService.getHashtagCounters();

        //THEN
        assertNotNull(retrievedHashtags);
        assertEquals(2, retrievedHashtags.size());
        verify(hashtagCounterRepository, times(1)).findAll();
    }

    @Test
    public void testAddHashtag() {
        //GIVEN
        HashtagCounter hashtagCounter = createHashtagCounterWithGivenId(1L);
        when(hashtagCounterRepository.save(hashtagCounter)).thenReturn(hashtagCounter);

        //WHEN
        HashtagCounter createdHashtagCounter = hashtagCounterService.addHashtagCounter(hashtagCounter);

        //THEN
        assertNotNull(createdHashtagCounter);
        assertEquals(1L, createdHashtagCounter.getId());
        assertEquals(12, createdHashtagCounter.getCount());
        verify(hashtagCounterRepository, times(1)).save(hashtagCounter);
    }

    @Test
    public void testUpdateHashtagCounter() {
        //GIVEN
        HashtagCounter hashtagCounter = createHashtagCounterWithGivenId(2L);
        when(hashtagCounterRepository.save(hashtagCounter)).thenReturn(hashtagCounter);

        //WHEN
        HashtagCounter updatedHashtagCounter = hashtagCounterService.updateHashtagCounter(hashtagCounter);

        //THEN
        assertNotNull(updatedHashtagCounter);
        assertEquals(2L, updatedHashtagCounter.getId());
        assertEquals(12, updatedHashtagCounter.getCount());
        verify(hashtagCounterRepository, times(1)).save(hashtagCounter);
    }

    @Test
    public void testDeleteHashtagCounter() {
        //GIVEN
        Long hashtagCounterId = 1L;
        HashtagCounter hashtagCounter = createHashtagCounterWithGivenId(hashtagCounterId);
        when(hashtagCounterRepository.findById(hashtagCounterId)).thenReturn(Optional.of(hashtagCounter));

        //WHEN
        hashtagCounterService.deleteHashtagCounter(hashtagCounterId);

        //THEN
        verify(hashtagCounterRepository, times(1)).deleteById(hashtagCounterId);
    }

    @Test
    public void testDeleteHashtagWhenHashtagNotExists() {
        //GIVEN
        Long hashtagCounterId = 1L;
        when(hashtagCounterRepository.existsById(hashtagCounterId)).thenReturn(false);

        //WHEN
        hashtagCounterService.deleteHashtagCounter(hashtagCounterId);

        //THEN
        verify(hashtagCounterRepository, never()).deleteById(anyLong());
    }

    private HashtagCounter createHashtagCounterWithGivenId(Long id) {
        HashtagCounter hashtagCounter = new HashtagCounter();
        hashtagCounter.setId(id);
        hashtagCounter.setCount(12);
        return hashtagCounter;
    }
}
