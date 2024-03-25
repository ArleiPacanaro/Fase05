package com.techchallenge05.mscarrinho.controller;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.entity.PagamentoCarrinho;
import com.techchallenge05.mscarrinho.service.PagamentoCarrinhoService;
import com.techchallenge05.mscarrinho.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Realizar pagamento do Carrinho.
 */
@Tag(name = "Pagamento Carrinho Controller",
        description = "O Pagamento Controlador de Carrinho expõe APIs REST para o Pagamento de Carrinho")
@RestController
@RequestMapping("carrinho/pagamento")
@AllArgsConstructor
public class PagamentoCarrinhoController {

  private PagamentoCarrinhoService pagamentoCarrinhoService;
  private TokenService tokenService;

  /**
  * Realizar pagamento do Carrinho.
  */
  @Operation(summary = "Realizar pagamento do Carrinho",
            description = "Realizar pagamento do Carrinho, passando os dados de pagamento")
  @PostMapping("/efetuar/{login}")
  public ResponseEntity<Carrinho> finalizarCarrinho(@PathVariable String login,
                                                    @RequestBody @Valid
                                                     PagamentoCarrinho pagamentoCarrinho) {
    String token = tokenService.getToken();

    Carrinho carrinhoFinalizado =
            pagamentoCarrinhoService.efetuandoCompraDoCarrinho(login, pagamentoCarrinho, token);

    return ResponseEntity.ok(carrinhoFinalizado);
    }
}
