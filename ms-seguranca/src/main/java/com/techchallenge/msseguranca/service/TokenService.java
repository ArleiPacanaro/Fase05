package com.techchallenge.msseguranca.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.techchallenge.msseguranca.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){

        try{

            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("loja-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar Token", exception);
        }

    }

    public String validateToken(String token){

        try {

            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("loja-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Erro ao validar Token", exception);
        }

    }


    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-03:00"));
    }

}