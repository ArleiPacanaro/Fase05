package com.techchallenge05.msestoque.controller;

import com.techchallenge05.msestoque.request.ProdutoRequest;
import com.techchallenge05.msestoque.response.ProdutoResponse;
import com.techchallenge05.msestoque.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<?> listarUmProduto(@PathVariable Integer produtoId) {
        return produtoService.listarUmProduto(produtoId);
    }
    @PostMapping
    public ResponseEntity cadastrarProduto(@RequestBody ProdutoRequest produto) {
        return produtoService.cadastrarProduto(produto);
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity atualizarProduto(@PathVariable Integer produtoId, @RequestBody ProdutoRequest novoProduto) {

        return produtoService.atualizarProduto(produtoId, novoProduto);
    }

    @DeleteMapping("/{produtoId}")
    public ResponseEntity excluirProduto(@PathVariable Integer produtoId) {
         return produtoService.excluirProduto(produtoId);
    }

    @PutMapping("/atualizar/estoque/{produtoId}/{quantidade}")
    public ResponseEntity atualizarEstoque(@PathVariable Integer produtoId, @PathVariable int quantidade) {
        return produtoService.atualizarEstoque(produtoId,quantidade);
    }

}
