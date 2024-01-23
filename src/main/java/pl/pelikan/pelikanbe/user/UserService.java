package pl.pelikan.pelikanbe.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounter;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounterRepository;
import pl.pelikan.pelikanbe.offer.Offer;
import pl.pelikan.pelikanbe.offer.OfferService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final HashtagCounterRepository hashtagCounterRepository;

    private final OfferService offerService;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        if (user.getOffers() != null) {
            setOffers(user);
        }
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user.getOffers() != null) {
            setOffers(user);
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            if (user.getHashTagCounters() != null) {
                for (HashtagCounter hashtagCounter : user.getHashTagCounters()) {
                    hashtagCounter.setUser(null);
                }
            }
            if (user.getOffers() != null) {
                for (Offer offer : user.getOffers()) {
                    List<User> users = offer.getUsers();
                    users.remove(user);
                    offer.setUsers(users);
                }
            }
            userRepository.deleteById(id);
        }
    }

    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }

    private void setOffers(User user) {
        List<Offer> offers = new ArrayList<>();
        for (Offer initOffer : user.getOffers()) {
            if (initOffer.getId() != null) {
                Offer offer = offerService.getOfferById(initOffer.getId());
                offers.add(offer);
            }
        }
        user.setOffers(offers);
    }

    public List<Offer> getBestFittingOffersForGivenUser(Long userId) {
        List<Offer> bestFittingOffers = new ArrayList<>();
        User user = getUserById(userId);
        if (user != null) {
            List<HashtagCounter> hashtagCounters = user.getHashTagCounters();
            List<Offer> offers = offerService.getOffers();
            if (hashtagCounters.size() > 0) {
                List<HashtagCounter> sortedList = hashtagCounters.stream()
                        .sorted(Comparator.comparingInt(HashtagCounter::getCount).reversed())
                        .toList();
                for (HashtagCounter hashtagCounter : sortedList) {
                    Hashtag hashtag = hashtagCounter.getHashtag();
                    if (hashtag != null) {
                        offers.stream()
                                .filter(offer -> !bestFittingOffers.contains(offer))
                                .filter(offer -> offer.getHashtags().contains(hashtag))
                                .forEach(bestFittingOffers::add);
                    }
                }
            } else {
                offers.stream()
                        .limit(12)
                        .forEach(bestFittingOffers::add);
            }
        } else {
            throw new EntityNotFoundException("User " + userId);
        }
        return bestFittingOffers;
    }

    public List<Offer> getOffersHistoryForGivenUser(Long userId) {
        User user = getUserById(userId);
        if (user != null) {
            return user.getOffers();
        } else {
            throw new EntityNotFoundException("User " + userId);
        }
    }

    public Offer buyOffer(Long userId, Long offerId) {
        User user = getUserById(userId);
        Offer offer = offerService.getOfferById(offerId);
        if (user != null && offer != null) {
            offer.setQuantity(offer.getQuantity() - 1);
            List<Offer> offers = user.getOffers();
            offers.add(offer);
            List<HashtagCounter> hashtagCounters = user.getHashTagCounters();
            List<Hashtag> offerHashtags = offer.getHashtags();
            for (Hashtag hashtag : offerHashtags) {
                HashtagCounter hashtagCounter = HashtagCounter.getHashtagCounterByHashtag(hashtagCounters, hashtag);
                if (hashtagCounter != null) {
                    hashtagCounter.setCount(hashtagCounter.getCount() + 1);
                } else {
                    hashtagCounter = new HashtagCounter();
                    hashtagCounter.setCount(1);
                    hashtagCounter.setHashtag(hashtag);
                    hashtagCounter.setUser(user);
                }
                hashtagCounterRepository.save(hashtagCounter);
            }
            userRepository.save(user);
            return offer;
        } else {
            throw new EntityNotFoundException("User/Offer " + userId + "/" + offerId);
        }
    }

    public User login(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new EntityNotFoundException("User " + email);
        }
    }
}
