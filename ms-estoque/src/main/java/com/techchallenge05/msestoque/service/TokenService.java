package com.techchallenge05.msestoque.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;



@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;


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


}
