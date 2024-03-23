package com.techchallenge.msseguranca.service;

import com.techchallenge.msseguranca.entity.User;
import com.techchallenge.msseguranca.repository.UserRepository;
import com.techchallenge.msseguranca.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public ResponseEntity register(UserRequest data){

        if(this.repository.findByCpf(data.cpf()) != null) return ResponseEntity.badRequest().body("Já existe um login com o cpf informado!");

        var encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        var newUser = new User(data.login(), encryptedPassword, data.cpf(), data.email(), data.role());

        this.repository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso.");

    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public Optional<User> getById(String id){
        return repository.findById(id);
    }

}