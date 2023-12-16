package pl.pelikan.pelikanbe.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pelikan.pelikanbe.hashtag.Hashtag;
import pl.pelikan.pelikanbe.hashtag_counter.HashtagCounter;
import pl.pelikan.pelikanbe.offer.Offer;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
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

    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }
}
