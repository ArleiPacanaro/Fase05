package com.techchallenge.msseguranca.controller;

import com.techchallenge.msseguranca.request.UserRequest;
import com.techchallenge.msseguranca.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controladora de Usuários.
 */
@Tag(name = "Usuários Controller", description = "Controlador de Usuários APIs REST para Usuários")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @Operation(summary = "Criar usuário", description = "Criar usuário persistindo na base")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResponseEntity register(@RequestBody @Valid UserRequest data) {
    return userService.register(data);
  }

  @Operation(summary = "Listar todos os usuários",
          description = "Listar todos os usuários persistidos na base")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public ResponseEntity getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * Listar todos os usuários.
   */
  @Operation(summary = "Listar usuário por ID",
          description = "Listar o usuário por ID persistido na base")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{id}")
  public ResponseEntity getAllUsers(@PathVariable String id) {

    var user = userService.getById(id);

    if (user.isPresent()) {
      return ResponseEntity.ok(userService.getAllUsers());
    }
    return ResponseEntity.noContent().build();

  }

}