package pl.pelikan.pelikanbe.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;
import pl.pelikan.pelikanbe.exception.UserAlreadyExistsException;
import pl.pelikan.pelikanbe.offer.Offer;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        if (service.existsUserById(id)) {
            return ResponseEntity.ok(service.getUserById(id));
        } else {
            throw new EntityNotFoundException("User " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) throws UserAlreadyExistsException {
        if (service.getUserByEmail(user.getEmail()) == null) {
            return ResponseEntity.ok(service.addUser(user));
        } else {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user) throws InvalidIdException {
        if (service.existsUserById(id)) {
            if (id.equals(user.getId())) {
                return ResponseEntity.ok(service.updateUser(user));
            } else {
                throw new InvalidIdException(id + " " + user.getId());
            }
        } else {
            throw new EntityNotFoundException("User " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        if (service.existsUserById(id)) {
            service.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("User " + id);
        }
    }

    @GetMapping("/getBestFittingOffersForGivenUser/{userId}")
    public ResponseEntity<List<Offer>> getBestFittingOffersForGivenUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getBestFittingOffersForGivenUser(userId));
    }

    @GetMapping("/offersHistory/{userId}")
    public ResponseEntity<List<Offer>> getOffersHistoryForGivenUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getOffersHistoryForGivenUser(userId));
    }

    @PostMapping("/buyOffer/{userId}/{offerId}")
    public ResponseEntity<Offer> buyOffer(@PathVariable Long userId, @PathVariable Long offerId) {
        return ResponseEntity.ok(service.buyOffer(userId, offerId));
    }
}
