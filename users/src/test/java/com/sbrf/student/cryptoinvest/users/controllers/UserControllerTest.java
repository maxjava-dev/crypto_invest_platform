// TODO: поправить тесты
//package com.sbrf.student.cryptoinvest.users.controllers;
//
//import com.sbrf.student.cryptoinvest.users.dto.UserDTO;
//import com.sbrf.student.cryptoinvest.users.model.User;
//import com.sbrf.student.cryptoinvest.users.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class UserControllerTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    private UserController userController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        userController = new UserController(userRepository);
//    }
//
//    @Test
//    void getUserByUsername_ExistingUser_ReturnsUserDTO() {
//        // Arrange
//        String username = "testUser";
//        User user = new User(username, "password", "Full Name", "test@example.com");
//        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
//
//        // Act
//        ResponseEntity<UserDTO> response = userController.getUserByUsername(username);
//
//        // Assert
//        assertEquals(ResponseEntity.ok(user.getDTO()), response);
//    }
//
//    @Test
//    void getUserByUsername_NonExistingUser_ReturnsNotFound() {
//        /// Arrange
//        String username = "testUser";
//        when(userRepository.findByUserName(username)).thenReturn(Optional.empty());
//
//        // Act
//        ResponseEntity<UserDTO> response = userController.getUserByUsername(username);
//
//        // Assert
//        assertEquals(ResponseEntity.notFound().build(), response);
//    }
//
//    @Test
//    void postUser_ValidUserDTO_ReturnsCreatedUserDTO() {
//        // Arrange
//        UserDTO userDTO = new UserDTO("testUser", "password", "Full Name", "test@example.com");
//        User user = new User(userDTO);
//        when(userRepository.save(user)).thenReturn(user);
//
//        // Act
//        ResponseEntity<UserDTO> response = userController.postUser(userDTO);
//
//        // Assert
//        assertEquals(ResponseEntity.ok(user.getDTO()), response);
//    }
//
//    @Test
//    void postUser_InvalidUserDTO_ReturnsBadRequest() {
//        // Arrange
//        UserDTO invalidUserDTO = new UserDTO(null, null, null, null);
//
//        // Act
//        ResponseEntity<UserDTO> response = userController.postUser(invalidUserDTO);
//
//        // Assert
//        assertEquals(ResponseEntity.badRequest().build(), response);
//    }
//}