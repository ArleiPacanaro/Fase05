package com.techchallenge.msseguranca.service;

import com.techchallenge.msseguranca.entity.User;
import com.techchallenge.msseguranca.entity.enums.UserRole;
import com.techchallenge.msseguranca.request.UserAuthRequest;
import com.techchallenge.msseguranca.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    TokenService tokenService;
    @InjectMocks
    AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Login")
    void testLogin() {
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(new User("id", "login", "password", "cpf", "email", UserRole.ADMIN));
        when(tokenService.generateToken(any(User.class))).thenReturn("generateTokenResponse");

        ResponseEntity<UserResponse> result = authenticationService.login(new UserAuthRequest("login", "password"));
        assertEquals(ResponseEntity.ok(new UserResponse("generateTokenResponse")), result);
    }
}