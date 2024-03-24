package com.techchallenge05.mscarrinho.controller;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.request.ItemCarrinhoRequest;
import com.techchallenge05.mscarrinho.service.CarrinhoService;
import com.techchallenge05.mscarrinho.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controladora de Carrinho.
 */
@Tag(name = "Carrinho Controller",
        description = "O Controlador de Carrinho expõe APIs REST para Carrinho")
@RestController
@RequestMapping("carrinho")
@AllArgsConstructor
public class CarrinhoController {

  private CarrinhoService carrinhoService;
  private TokenService tokenService;

  /**
   * Criar Carrinho.
  */
  @Operation(summary = "Criar Carrinho",
          description =
                  "Criar Carrinho pelo Login do usuário")
  @PostMapping
  public ResponseEntity<String> criarCarrinho(@RequestParam String login) {

    String token = tokenService.getToken();
    Carrinho carrinhocriado = carrinhoService.criarCarrinho(login, token);
    return  ResponseEntity.ok("Id do Carrinho : " + carrinhocriado.getId());

  }

  /**
   * Adicionar produtos ao Carrinho.
   */
  @Operation(summary = "Adicionar produtos ao Carrinho",
          description = "Adicionar produtos ao carriho do usuário pelo login, "
                 +  "recuperando o carrinho que esta aberto")
  @PostMapping("/adicionarproduto/{login}")
  public ResponseEntity<?> adicionarItemCarrinho(@PathVariable String login,
                                                   @Valid  @RequestBody ItemCarrinhoRequest item) {
    String token = tokenService.getToken();
    Carrinho carrinho = carrinhoService.adicionarCarrinho(login, item, token);
    return ResponseEntity.ok(carrinho);
  }

  /**
  * Adicionar produtos ao Carrinho.
  */
  @Operation(summary = "Remover produtos ao Carrinho",
          description = "Remover produtos ao carriho do usuário pelo login, "
                  + "recuperando o carrinho que esta aberto")
  @DeleteMapping("/removerproduto/{login}")
  public ResponseEntity<Carrinho> removerItemCarrinho(
            @PathVariable String login,
            @RequestBody @Valid  Integer idProduto
  ) {
    String token = tokenService.getToken();
    Carrinho carrinho = carrinhoService.removerItemCarrinho(login, idProduto, token);
    return ResponseEntity.ok(carrinho);
  }


  /**
  * listar todos os Carrinhos.
  */
  @Operation(summary = "listar todos os Carrinhos", description = "listar todos os Carrinhos")
  @GetMapping
  public ResponseEntity<List<Carrinho>> listarTodosCarrinhos() {
    return  ResponseEntity.ok(carrinhoService.listarCarrinhos());
  }

  /**
  * listar  Carrinho por ID.
  */
  @Operation(summary = "listar o Carrinho por ID", description = "listar o Carrinho por ID")
  @GetMapping("/{id}")
  public ResponseEntity<Carrinho> listarCarrinhoPorId(String id) {
    return  ResponseEntity.ok(carrinhoService.listarCarrinhoPorId(id));
  }

  /**
  * listar o Carrinho por Login.
  */
  @Operation(summary = "listar o Carrinho por Login", description = "listar o Carrinho por Login")
  @GetMapping("/listarPorlogin/{login}")
  public ResponseEntity<Carrinho> listarCarrinhoPorLogin(String login) {
    return  ResponseEntity.ok(carrinhoService.listarCarrinhoPorId(login));
  }

}