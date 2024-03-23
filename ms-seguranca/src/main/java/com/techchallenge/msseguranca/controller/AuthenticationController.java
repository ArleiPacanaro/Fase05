package com.techchallenge.msseguranca.controller;

import com.techchallenge.msseguranca.request.UserAuthRequest;
import com.techchallenge.msseguranca.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserAuthRequest data){
        return authenticationService.login(data);
    }

    @GetMapping("/validate")
    public ResponseEntity<List<String>> validate() {

        var authentication = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities();

        var listaPrivilegios = new ArrayList<String>();

        authentication.forEach(aut -> listaPrivilegios.add(aut.getAuthority().toString()));

        return ResponseEntity.ok(listaPrivilegios);

    }

}
