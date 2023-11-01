package com.giriraj.usermanegementservice.service;

import com.giriraj.usermanegementservice.exception.EmailAlreadyExists;
import com.giriraj.usermanegementservice.exception.UserNotFoundException;
import com.giriraj.usermanegementservice.model.Users;
import com.giriraj.usermanegementservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void createUser() throws EmailAlreadyExists {
        Users user = new Users();
        user.setId(1L);
        user.setName("testUser");
        user.setPassword("password");
        Mockito.when(userRepository.save(Mockito.any(Users.class))).thenReturn(user);
        Users createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());

        assertEquals(user.getPassword(), createdUser.getPassword());


        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(Users.class));
    }

    @Test
    void getUserById() {
        Users user = new Users();
        user.setId(1L);
        user.setName("testUser");
        user.setPassword("password");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        try {
            Users foundUser = userService.getUserById(1L);
            assertNotNull(foundUser);
            assertEquals(user.getId(), foundUser.getId());
            assertEquals(user.getName(), foundUser.getName());
            assertEquals(user.getPassword(), foundUser.getPassword());
        } catch (UserNotFoundException e) {
            fail("UserNotFoundException should not be thrown");
        }

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);


    }
    @Test
    public void testGetUserByIdUserNotFound() {

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });


        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }
}