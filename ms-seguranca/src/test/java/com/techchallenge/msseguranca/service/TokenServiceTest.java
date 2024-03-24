package com.techchallenge.msseguranca.service;

import com.techchallenge.msseguranca.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TokenServiceTest {
    @Mock
    private User user;
    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "testSecret");
    }

    @Test
    @DisplayName("Test Generate Token")
    void testGenerateToken() {
        when(user.getLogin()).thenReturn("login");
        String token = tokenService.generateToken(user);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("Test Validate Token")
    void testValidateToken() {
        when(user.getLogin()).thenReturn("login");
        String token = tokenService.generateToken(user);
        String subject = tokenService.validateToken(token);
        assertEquals(user.getLogin(), subject);
    }
}