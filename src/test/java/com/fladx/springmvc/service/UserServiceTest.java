package com.fladx.springmvc.service;

import com.fladx.springmvc.model.User;
import com.fladx.springmvc.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = new User(1L, "John Doe", "johndoe@example.com");
        user2 = new User(2L, "Jane Smith", "janesmith@example.com");
    }

    @Test
    void testFindAll() {
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals(user1, result.get(0));
        assertEquals(user2, result.get(1));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        User result = userService.findById(1L);

        assertEquals(user1, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        when(userRepository.save(user1)).thenReturn(user1);

        User result = userService.create(user1);

        assertEquals(user1, result);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testUpdate_UserExists() {
        User updatedUserDetails = new User(null, "Updated Name", "updated@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);

        User result = userService.update(1L, updatedUserDetails);

        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testUpdate_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User updatedUserDetails = new User(null, "Updated Name", "updated@example.com");
        assertThrows(EntityNotFoundException.class, () -> userService.update(1L, updatedUserDetails));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDelete_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        doNothing().when(userRepository).delete(user1);

        userService.delete(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    void testDelete_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(1L));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).delete(any(User.class));
    }
}
