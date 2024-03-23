package com.techchallenge05.mscarrinho.controller;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.entity.ItemCarrinho;
import com.techchallenge05.mscarrinho.entity.PagamentoCarrinho;
import com.techchallenge05.mscarrinho.request.ItemCarrinhoRequest;
import com.techchallenge05.mscarrinho.service.CarrinhoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/carrinho")
@AllArgsConstructor
public class CarrinhoController {

    private CarrinhoService carrinhoService;

    @PostMapping
    public ResponseEntity<String> criarCarrinho(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                  @RequestParam String login
                                                  ) {

        Carrinho carrinhocriado = carrinhoService.criarCarrinho(login,authorizationHeader);
        return  ResponseEntity.ok("Id do Carrinho : " + carrinhocriado.getId());

    }


    @PostMapping("/adicionarproduto/{login}")
    public ResponseEntity<Carrinho> adicionarItemCarrinho(@PathVariable String login,
                                                          @RequestHeader(value = "Authorization") String authorizationHeader,
                                                          @RequestBody ItemCarrinhoRequest item) {
        return ResponseEntity.ok(carrinhoService.AdicionarCarrinho(login,item,authorizationHeader));
    }


    @DeleteMapping("/removerproduto/{login}")
    public ResponseEntity<Carrinho> removerItemCarrinho(
            @PathVariable String login,
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody Integer idProduto
    )
    {

        Carrinho carrinho = carrinhoService.removerItemCarrinho(login,idProduto,authorizationHeader);
        return ResponseEntity.ok(carrinho);
    }


    @PostMapping("/finalizarcompra/{login}")
    public ResponseEntity<Carrinho> finalizarCarrinho(@PathVariable String login,
                                                      @RequestHeader(value = "Authorization") String authorizationHeader,
                                                      @RequestBody PagamentoCarrinho pagamentoCarrinho) {

        Carrinho carrinhoFinalizado = carrinhoService.efetuandoCompraDoCarrrinho(login,pagamentoCarrinho,authorizationHeader);
        return ResponseEntity.ok(carrinhoFinalizado);
    }

    @GetMapping
    public ResponseEntity<List<Carrinho>> listarTodosCarrinhos(){

        return  ResponseEntity.ok(carrinhoService.listarCarrinhos());


    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> listarCarrinhoPorId(String id){

        return  ResponseEntity.ok(carrinhoService.listarCarrinhoPorId(id));


    }

    @GetMapping("/login/{login}")
    public ResponseEntity<Carrinho> listarCarrinhoPorLogin(String login){

        return  ResponseEntity.ok(carrinhoService.listarCarrinhoPorId(login));

    }

}