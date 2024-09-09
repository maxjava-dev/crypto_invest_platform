package com.sbrf.student.cryptoinvest.backtofront.api;

import com.sbrf.student.cryptoinvest.backtofront.models.User;
import com.sbrf.student.cryptoinvest.backtofront.utils.RestTemplateClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserApiTest {

    @Mock
    private RestTemplateClass restTemplate;

    @InjectMocks
    private UserApi userApi;

    private static final String API_PATH = "http://localhost:8081/users";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userApi = new UserApi(restTemplate);
    }

    @Test
    void testGetUserByUsername_ExistingUser_ReturnsUser() {
        // Arrange
        String username = "testuser";
        User user = new User(username, "Test", "User", "test@test.com");
        when(restTemplate.getForEntity(API_PATH + "/" + username, User.class))
                .thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        // Act
        Optional<User> response = userApi.getUserByUsername(username);

        // Assert
        assertTrue(response.isPresent());
        assertEquals(user, response.get());
    }

    @Test
    void testGetUserByUsername_NonExistingUser_ReturnsEmptyOptional() {
        // Arrange
        String username = "nonexistinguser";
        when(restTemplate.getForEntity(API_PATH + "/" + username, User.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Act
        Optional<User> response = userApi.getUserByUsername(username);

        // Assert
        assertTrue(response.isEmpty());
    }

    @Test
    void testPostUser_ValidUser_ReturnsUser() {
        // Arrange
        User user = new User("testuser", "Test", "User", "test@test.com");
        when(restTemplate.postForEntity(API_PATH, user, User.class))
                .thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        // Act
        Optional<User> response = userApi.postUser(user);

        // Assert
        assertTrue(response.isPresent());
        assertEquals(user, response.get());
    }

    @Test
    void testPostUser_InvalidUser_ReturnsEmptyOptional() {
        // Arrange
        User user = new User(null, null, null, null);
        when(restTemplate.postForEntity(API_PATH, user, User.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        // Act
        Optional<User> response = userApi.postUser(user);

        // Assert
        assertTrue(response.isEmpty());
    }
}