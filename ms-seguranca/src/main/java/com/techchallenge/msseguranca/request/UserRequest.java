package com.techchallenge.msseguranca.request;

import com.techchallenge.msseguranca.entity.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

/**
 * DTO para criação-consulta de usuários.
 */
public record UserRequest(
        @NotBlank String login,
        @NotBlank String password,
        @CPF
        @NotBlank
        String cpf,
        @Email
        @NotBlank
        String email,
        UserRole role) {
}