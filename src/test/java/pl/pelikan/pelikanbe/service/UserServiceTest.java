package pl.pelikan.pelikanbe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.pelikan.pelikanbe.offer.OfferService;
import pl.pelikan.pelikanbe.user.User;
import pl.pelikan.pelikanbe.user.UserRepository;
import pl.pelikan.pelikanbe.user.UserService;
import pl.pelikan.pelikanbe.user.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class UserServiceTest {

    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        OfferService offerService = Mockito.mock(OfferService.class);
        userService = new UserService(userRepository, offerService);
    }

    @Test
    public void testGetUserById() {
        //GIVEN
        Long userId = 1L;
        User user = createUserWithGivenId(1L);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //WHEN
        User retrievedUser = userService.getUserById(userId);

        //THEN
        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
        assertEquals("admin@pelikan.com", retrievedUser.getEmail());
        assertEquals("Admin", retrievedUser.getFirstName());
        assertEquals("Admin", retrievedUser.getLastName());
        assertEquals("PelikanVacation", retrievedUser.getPassword());
        assertEquals("+48 123456789", retrievedUser.getPhoneNumber());
        assertEquals(UserType.ADMIN, retrievedUser.getUserType());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUsers() {
        //GIVEN
        List<User> users = new ArrayList<>();
        User user1 = createUserWithGivenId(1L);
        User user2 = createUserWithGivenId(2L);
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);

        //WHEN
        List<User> retrievedUsers = userService.getUsers();

        //THEN
        assertNotNull(retrievedUsers);
        assertEquals(2, retrievedUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testAddUser() {
        //GIVEN
        User user = createUserWithGivenId(2L);
        when(userRepository.save(user)).thenReturn(user);

        //WHEN
        User createdUser = userService.addUser(user);

        //THEN
        assertNotNull(createdUser);
        assertEquals(2L, createdUser.getId());
        assertEquals("admin@pelikan.com", createdUser.getEmail());
        assertEquals("Admin", createdUser.getFirstName());
        assertEquals("Admin", createdUser.getLastName());
        assertEquals("PelikanVacation", createdUser.getPassword());
        assertEquals("+48 123456789", createdUser.getPhoneNumber());
        assertEquals(UserType.ADMIN, createdUser.getUserType());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserCounter() {
        //GIVEN
        User user = createUserWithGivenId(3L);
        when(userRepository.save(user)).thenReturn(user);

        //WHEN
        User updatedUser = userService.updateUser(user);

        //THEN
        assertNotNull(updatedUser);
        assertEquals(3L, updatedUser.getId());
        assertEquals("admin@pelikan.com", updatedUser.getEmail());
        assertEquals("Admin", updatedUser.getFirstName());
        assertEquals("Admin", updatedUser.getLastName());
        assertEquals("PelikanVacation", updatedUser.getPassword());
        assertEquals("+48 123456789", updatedUser.getPhoneNumber());
        assertEquals(UserType.ADMIN, updatedUser.getUserType());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() {
        //GIVEN
        Long userId = 1L;
        User user = createUserWithGivenId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //WHEN
        userService.deleteUser(userId);

        //THEN
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUserWhenUserNotExists() {
        //GIVEN
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        //WHEN
        userService.deleteUser(userId);

        //THEN
        verify(userRepository, never()).deleteById(anyLong());
    }

    private User createUserWithGivenId(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail("admin@pelikan.com");
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setPassword("PelikanVacation");
        user.setPhoneNumber("+48 123456789");
        user.setUserType(UserType.ADMIN);
        return user;
    }
}
