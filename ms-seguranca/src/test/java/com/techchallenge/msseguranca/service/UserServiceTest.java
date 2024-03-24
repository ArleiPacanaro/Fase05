package com.techchallenge.msseguranca.service;

import com.techchallenge.msseguranca.entity.User;
import com.techchallenge.msseguranca.entity.enums.UserRole;
import com.techchallenge.msseguranca.repository.UserRepository;
import com.techchallenge.msseguranca.request.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;

@DisplayName("User Service Test")
class UserServiceTest {
    @Mock
    UserRepository repository;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Register")
    void testRegister() {
        when(repository.findByCpf(anyString())).thenReturn(null);
        User savedUser = new User("id", "login", "password", "cpf", "email", UserRole.ADMIN);
        when(repository.save(any())).thenReturn(savedUser);

        ResponseEntity result = userService.register(new UserRequest("login", "password", "cpf", "email", UserRole.ADMIN));
        Assertions.assertEquals(new ResponseEntity("Usuário registrado com sucesso.", HttpStatus.CREATED), result);
    }

    @Test
    @DisplayName("Test Get All Users")
    void testGetAllUsers() {
        when(repository.findAll()).thenReturn(List.of(new User("id", "login", "password", "cpf", "email", UserRole.ADMIN)));

        List<User> result = userService.getAllUsers();
        Assertions.assertEquals(List.of(new User("id", "login", "password", "cpf", "email", UserRole.ADMIN)), result);
    }

    @Test
    @DisplayName("Test Get User By Id")
    void testGetById() {
        when(repository.findById(any())).thenReturn(Optional.of(new User("id", "login", "password", "cpf", "email", UserRole.ADMIN)));

        Optional<User> result = userService.getById("id");
        Assertions.assertEquals(Optional.of(new User("id", "login", "password", "cpf", "email", UserRole.ADMIN)), result);
    }
}

