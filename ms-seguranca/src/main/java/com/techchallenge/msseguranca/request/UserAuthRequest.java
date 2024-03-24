package com.techchallenge.msseguranca.request;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para recuperação de Token.
 */
public record UserAuthRequest(@NotBlank String login, @NotBlank  String password) {
}
