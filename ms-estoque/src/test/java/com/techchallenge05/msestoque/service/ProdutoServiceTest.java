package com.techchallenge05.msestoque.service;

import com.techchallenge05.msestoque.entity.Produto;
import com.techchallenge05.msestoque.repository.ProdutoRepository;
import com.techchallenge05.msestoque.request.ProdutoRequest;
import com.techchallenge05.msestoque.response.ProdutoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Produto Service Test")
class ProdutoServiceTest {

    // TODO: Refactor this test class

    @Mock
    ProdutoRepository produtoRepository;
    @InjectMocks
    ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Listar Produtos")
    void testListarProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d)));

        ResponseEntity<List<ProdutoResponse>> result = produtoService.listarProdutos();
        Assertions.assertEquals(ResponseEntity.ok(List.of(new ProdutoResponse(Integer.valueOf(0), "nome", "descricao", 0, 0d))), result);
    }

    @Test
    @DisplayName("Test Listar Um Produto")
    void testListarUmProduto() {
        when(produtoRepository.findById(any())).thenReturn(Optional.of(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d)));

        ResponseEntity<?> result = produtoService.listarUmProduto(Integer.valueOf(0));
        Assertions.assertEquals(ResponseEntity.ok(new ProdutoResponse(Integer.valueOf(0), "nome", "descricao", 0, 0d)), result);
    }

    @Test
    @DisplayName("Test Cadastrar Produto")
    void testCadastrarProduto() {
        when(produtoRepository.save(any())).thenReturn(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d));
        when(produtoRepository.existsById(any())).thenReturn(false);

        ResponseEntity result = produtoService.cadastrarProduto(new ProdutoRequest(Integer.valueOf(0), "nome", "descricao", 0, 0d));
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED)
                .body(new ProdutoResponse(Integer.valueOf(0), "nome", "descricao", 0, 0d)), result);
    }

    @Test
    @DisplayName("Test Atualizar Produto")
    void testAtualizarProduto() {
        when(produtoRepository.save(any())).thenReturn(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d));
        when(produtoRepository.findById(any())).thenReturn(Optional.of(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d)));

        ResponseEntity result = produtoService.atualizarProduto(Integer.valueOf(0), new ProdutoRequest(Integer.valueOf(0), "nome", "descricao", 0, 0d));
        Assertions.assertEquals(ResponseEntity.ok()
                .body(new ProdutoResponse(Integer.valueOf(0), "nome", "descricao", 0, 0d)), result);
    }

    @Test
    @DisplayName("Test Excluir Produto")
    void testExcluirProduto() {
        when(produtoRepository.findById(any())).thenReturn(Optional.of(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d)));

        ResponseEntity result = produtoService.excluirProduto(Integer.valueOf(0));
        Assertions.assertEquals(ResponseEntity.ok()
                .build(), result);
    }

    @Test
    @DisplayName("Test Atualizar Estoque")
    void testAtualizarEstoque() {
        when(produtoRepository.save(any())).thenReturn(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d));
        when(produtoRepository.findById(any())).thenReturn(Optional.of(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d)));

        ResponseEntity result = produtoService.atualizarEstoque(Integer.valueOf(0), 0);
        Assertions.assertEquals(ResponseEntity.ok()
                .body(new Produto(Integer.valueOf(0), "nome", "descricao", 0, 0d)), result);
    }
}