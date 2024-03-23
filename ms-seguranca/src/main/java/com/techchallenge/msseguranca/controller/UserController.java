package com.techchallenge.msseguranca.controller;

import com.techchallenge.msseguranca.request.UserRequest;
import com.techchallenge.msseguranca.service.AuthenticationService;
import com.techchallenge.msseguranca.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity register(@RequestBody @Valid UserRequest data){
        return userService.register(data);
    }
    @GetMapping
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity getAllUsers(@PathVariable String id){

        var user = userService.getById(id);

        if (user.isPresent())
            return ResponseEntity.ok(userService.getAllUsers());

        return ResponseEntity.noContent().build();

    }

}