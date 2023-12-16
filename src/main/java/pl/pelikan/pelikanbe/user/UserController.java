package pl.pelikan.pelikanbe.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelikan.pelikanbe.exception.InvalidIdException;

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
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(service.addUser(user));
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
}
