package com.techchallenge.msseguranca.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum de roles de acessos.
 */
@Getter
@AllArgsConstructor
public enum UserRole {
  ADMIN("admin"),
  USER("user");

  private String role;

}