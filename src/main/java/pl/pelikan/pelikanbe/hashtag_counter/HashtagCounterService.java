package pl.pelikan.pelikanbe.hashtag_counter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.hashtag.HashtagService;
import pl.pelikan.pelikanbe.user.User;
import pl.pelikan.pelikanbe.user.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class HashtagCounterService {

    private final HashtagCounterRepository repository;

    private final UserService userService;

    private final HashtagService hashtagService;

    public HashtagCounter getHashtagCounterById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<HashtagCounter> getHashtagCounters() {
        return repository.findAll();
    }

    public HashtagCounter addHashtagCounter(HashtagCounter hashtagCounter) {
        User user = hashtagCounter.getUser();
        if (user != null && user.getId() != null) {
            setUserById(hashtagCounter, user.getId());
        }
        Hashtag hashtag = hashtagCounter.getHashtag();
        if (hashtag != null && hashtag.getId() != null) {
            setHashtagById(hashtagCounter, hashtag.getId());
        }
        return repository.save(hashtagCounter);
    }

    public HashtagCounter updateHashtagCounter(HashtagCounter hashtagCounter) {
        User user = hashtagCounter.getUser();
        if (user != null && user.getId() != null) {
            setUserById(hashtagCounter, user.getId());
        }
        Hashtag hashtag = hashtagCounter.getHashtag();
        if (hashtag != null && hashtag.getId() != null) {
            setHashtagById(hashtagCounter, hashtag.getId());
        }
        return repository.save(hashtagCounter);
    }

    public void deleteHashtagCounter(Long id) {
        HashtagCounter hashtagCounter = getHashtagCounterById(id);
        if (hashtagCounter.getUser() != null) {
            hashtagCounter.setUser(null);
        }
        if (hashtagCounter.getHashtag() != null) {
            hashtagCounter.setHashtag(null);
        }
        repository.deleteById(id);
    }

    public boolean existsHashtagCounterById(Long id) {
        return repository.existsById(id);
    }

    private void setUserById(HashtagCounter hashtagCounter, Long id) {
        User user = userService.getUserById(id);
        hashtagCounter.setUser(user);
    }

    private void setHashtagById(HashtagCounter hashtagCounter, Long id) {
        Hashtag hashtag = hashtagService.getHashtagById(id);
        hashtagCounter.setHashtag(hashtag);
    }
}
