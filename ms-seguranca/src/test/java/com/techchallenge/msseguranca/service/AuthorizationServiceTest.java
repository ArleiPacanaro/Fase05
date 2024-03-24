package com.techchallenge.msseguranca.service;

import com.techchallenge.msseguranca.entity.User;
import com.techchallenge.msseguranca.entity.enums.UserRole;
import com.techchallenge.msseguranca.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;

class AuthorizationServiceTest {
    @Mock
    UserRepository repository;
    @InjectMocks
    AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Load User By Username")
    void testLoadUserByUsername() {
        when(repository.findByLogin(anyString())).thenReturn(new User("id", "login", "password", "cpf", "email", UserRole.ADMIN));

        UserDetails result = authorizationService.loadUserByUsername("username");
        Assertions.assertEquals(new User("id", "login", "password", "cpf", "email", UserRole.ADMIN), result);
    }
}
