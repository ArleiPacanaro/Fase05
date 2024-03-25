package com.techchallenge.msseguranca.controller;

import com.techchallenge.msseguranca.request.UserAuthRequest;
import com.techchallenge.msseguranca.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controladora de Autenticação.
 */

@Tag(name = "Autenticação Controller",
        description = "Controlador de Autenticação APIs REST para Autenticação")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @Operation(summary = "Criar Token", description = "Criar Token")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid UserAuthRequest data) {
    return authenticationService.login(data);
  }

  /**
   * Metodo para validar token e retornar roles.
   */
  @Operation(summary = "Validar Token e retornar privilégios",
          description = "Validar Token e retornar privilégios")
  @ResponseStatus(HttpStatus.CREATED)
  @GetMapping("/validate")
  public ResponseEntity<List<String>> validate() {

    var authentication =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

    var listaPrivilegios = new ArrayList<String>();

    authentication.forEach(aut -> listaPrivilegios.add(aut.getAuthority().toString()));

    return ResponseEntity.ok(listaPrivilegios);

  }

}
