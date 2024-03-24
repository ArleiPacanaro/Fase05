package com.techchallenge05.msestoque.controller;

import com.techchallenge05.msestoque.request.ProdutoRequest;
import com.techchallenge05.msestoque.response.ProdutoResponse;
import com.techchallenge05.msestoque.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controladora de Autenticação.
 */
@Tag(name = "Estoque Controller",
        description = "O Controlador de estoque-produtos expõe APIs REST para estoque-produtos")
@RestController
@RequestMapping("produto")
@RequiredArgsConstructor
public class ProdutoController {

  private final ProdutoService produtoService;

  @Operation(summary = "Listar Produtos em estoque",
          description = "Listar Produtos em estoque")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public ResponseEntity<List<ProdutoResponse>> listarProdutos() {

    return produtoService.listarProdutos();
  }

  @Operation(summary = "Listar Produto em estoque por ID",
      description = "Listar Produto em estoque por ID")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{produtoId}")
  public ResponseEntity<?> listarUmProduto(@PathVariable Integer produtoId) {
    return produtoService.listarUmProduto(produtoId);
  }

  @Operation(summary = "Cadastrar Produto em estoque",
          description = "Cadastrar Produto em estoque persistindo no banco de dados")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResponseEntity cadastrarProduto(@RequestBody @Valid ProdutoRequest produto) {
    return produtoService.cadastrarProduto(produto);
  }

  @Operation(summary = "Atualizar Produto em estoque",
          description = "Atualizar Produto em estoque persistindo no banco de dados")
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{produtoId}")
  public ResponseEntity atualizarProduto(@PathVariable Integer produtoId,
                                         @RequestBody @Valid ProdutoRequest novoProduto) {

    return produtoService.atualizarProduto(produtoId, novoProduto);
  }

  @Operation(summary = "Excluir Produto em estoque por ID",
          description = "Excuir Produto em estoque persistindo no banco de dados")
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{produtoId}")
  public ResponseEntity excluirProduto(@PathVariable Integer produtoId) {
    return produtoService.excluirProduto(produtoId);
  }

  @Operation(summary = "Atualizar Produto em estoque por ID",
          description = "Atualizar Produto em estoque persistindo no banco de dados")
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/atualizar/estoque/{produtoId}/{quantidade}")
  public ResponseEntity atualizarEstoque(@PathVariable Integer produtoId,
                                         @PathVariable int quantidade) {
    return produtoService.atualizarEstoque(produtoId, quantidade);
  }

}
