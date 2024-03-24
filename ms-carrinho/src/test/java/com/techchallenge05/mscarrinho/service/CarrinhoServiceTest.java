package com.techchallenge05.mscarrinho.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.entity.ItemCarrinho;
import com.techchallenge05.mscarrinho.entity.PagamentoCarrinho;
import com.techchallenge05.mscarrinho.repository.CarrinhoRepository;
import com.techchallenge05.mscarrinho.request.ItemCarrinhoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DisplayName("Carrinho Service Test")
public class CarrinhoServiceTest {
    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CarrinhoService carrinhoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Criar Carrinho")
    void testCriarCarrinho() {
        when(carrinhoRepository.findByLoginCliente(anyString(), anyBoolean())).thenReturn(Optional.empty());
        Carrinho expectedCarrinho = new Carrinho("id", "loginCliente", List.of(new ItemCarrinho(0, "nome", "descricao", 0, 0d)), LocalDateTime.now(), 0d, true, new PagamentoCarrinho("cartao", "validade", "bandeira", 0));
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(expectedCarrinho);

        Carrinho result = carrinhoService.criarCarrinho("login", "authorizationHeader");
        Assertions.assertEquals(expectedCarrinho, result);
    }


}
