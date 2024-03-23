package com.techchallenge05.mscarrinho.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge05.mscarrinho.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  TokenService tokenService;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
                    , FilterChain filterChain) throws ServletException, IOException {

   var token = this.recoverToken(request);

   final String AUTH_URL = "http://localhost:8081/auth/validate";

   final RestTemplate restTemplate = new RestTemplate();


   if (token != null) {

      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(token);
      HttpEntity<Void> entity = new HttpEntity<>(headers);

      try {
           ResponseEntity<Void> authResponse = restTemplate.exchange(AUTH_URL, HttpMethod.GET, entity, Void.class);
           if (authResponse.getStatusCode().is2xxSuccessful()) {

                            String username = tokenService.validateToken(token);

                            JsonNode permissao = objectMapper.readTree(String.valueOf(authResponse.getBody()));
                            List<SimpleGrantedAuthority> permissoes = new ArrayList<>();

                            Iterator<Map.Entry<String, JsonNode>> fields = permissao.fields();

                            while (fields.hasNext()) {
                                Map.Entry<String, JsonNode> field = fields.next();
                                permissoes.add(new SimpleGrantedAuthority(field.getValue().toString()));
                            }


                            UsernamePasswordAuthenticationToken tokenValidado =
                                    new UsernamePasswordAuthenticationToken(
                                            username, null, new ArrayList<>());

                            SecurityContextHolder.getContext().setAuthentication(tokenValidado);

                            filterChain.doFilter(request, response);
                        }
                    } catch (HttpClientErrorException e) {
                        response.setStatus(403);
                        return;
                    }
                }


            }

            private String recoverToken(HttpServletRequest request) {
                    var authHeader = request.getHeader("Authorization");
                    if(authHeader == null) return null;
                    return authHeader.replace("Bearer ", "");

            }


    }




