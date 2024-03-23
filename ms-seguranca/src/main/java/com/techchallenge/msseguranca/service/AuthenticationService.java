package com.techchallenge.msseguranca.service;

import com.techchallenge.msseguranca.entity.User;
import com.techchallenge.msseguranca.request.UserAuthRequest;
import com.techchallenge.msseguranca.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService   {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public ResponseEntity login(UserAuthRequest data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new UserResponse(token));

    }

}