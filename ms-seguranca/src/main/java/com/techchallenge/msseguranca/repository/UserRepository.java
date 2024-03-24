package com.techchallenge.msseguranca.repository;

import com.techchallenge.msseguranca.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface para JPA.
 */
public interface UserRepository extends JpaRepository<User, String> {
  UserDetails findByLogin(String login);

  UserDetails findByCpf(String cpf);
}