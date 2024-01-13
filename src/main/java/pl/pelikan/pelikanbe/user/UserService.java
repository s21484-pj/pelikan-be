package pl.pelikan.pelikanbe.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounter;
import pl.pelikan.pelikanbe.offer.Offer;
import pl.pelikan.pelikanbe.offer.OfferService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}
