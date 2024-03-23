package com.techchallenge05.msestoque.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.techchallenge05.msestoque.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String AUTH_URL = "http://localhost:8081/auth/validate";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {

            var headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            try {

                var authResponse = restTemplate.exchange(AUTH_URL, HttpMethod.GET, entity, String.class);

                if (authResponse.getStatusCode().is2xxSuccessful()) {

                    var username = tokenService.validateToken(token);
                    var permissao = objectMapper.readTree(String.valueOf(authResponse.getBody()));
                    var permissoes = new ArrayList<SimpleGrantedAuthority>();
                    var fields = permissao.elements();

                    while (fields.hasNext()) {
                        permissoes.add(new SimpleGrantedAuthority(fields.next().textValue()));
                    }

                    var tokenValidado = new UsernamePasswordAuthenticationToken(username, null, permissoes);

                    SecurityContextHolder.getContext().setAuthentication(tokenValidado);

                    filterChain.doFilter(request, response);
                }
            } catch (HttpClientErrorException e) {
                response.setStatus(403);
                return;
            }
        }
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        return authHeader != null ? authHeader.replace("Bearer ", "") : null;
    }

}
