package com.techchallenge05.mscarrinho.service;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.entity.ItemCarrinho;
import com.techchallenge05.mscarrinho.entity.PagamentoCarrinho;
import com.techchallenge05.mscarrinho.repository.CarrinhoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PagamentoCarrinhoServiceTest {
    @Mock
    CarrinhoRepository carrinhoRepository;
    @InjectMocks
    PagamentoCarrinhoService pagamentoCarrinhoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Efetuando Compra Do Carrinho")
    void testEfetuandoCompraDoCarrinho() {
        Carrinho existingCarrinho = new Carrinho("id", "loginCliente", List.of(new ItemCarrinho(0, "nome", "descricao", 0, 0d)), LocalDateTime.of(2024, Month.MARCH, 24, 22, 27, 19), 0d, true, new PagamentoCarrinho("cartao", "validade", "bandeira", 0));
        when(carrinhoRepository.findByLoginCliente(anyString(), anyBoolean())).thenReturn(Optional.of(existingCarrinho));

        Carrinho savedCarrinho = new Carrinho("id", "loginCliente", List.of(new ItemCarrinho(0, "nome", "descricao", 0, 0d)), LocalDateTime.of(2024, Month.MARCH, 24, 22, 27, 19), 0d, false, new PagamentoCarrinho("cartao", "validade", "bandeira", 0));
        when(carrinhoRepository.save(any())).thenReturn(savedCarrinho);

        Carrinho result = pagamentoCarrinhoService.efetuandoCompraDoCarrinho("login", new PagamentoCarrinho("cartao", "validade", "bandeira", 0), "authorizationHeader");
        Assertions.assertEquals(savedCarrinho, result);
    }
}