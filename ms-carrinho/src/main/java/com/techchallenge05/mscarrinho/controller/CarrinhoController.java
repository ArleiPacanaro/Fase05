package com.techchallenge05.mscarrinho.controller;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.entity.ItemCarrinho;
import com.techchallenge05.mscarrinho.entity.PagamentoCarrinho;
import com.techchallenge05.mscarrinho.request.ItemCarrinhoRequest;
import com.techchallenge05.mscarrinho.service.CarrinhoService;
import com.techchallenge05.mscarrinho.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("carrinho")
@AllArgsConstructor
public class CarrinhoController {

    private CarrinhoService carrinhoService;
    private TokenService tokenService;



    @PostMapping
    public ResponseEntity<String> criarCarrinho(@RequestParam String login) {

        String token = tokenService.getToken();
        Carrinho carrinhocriado = carrinhoService.criarCarrinho(login,token);
        return  ResponseEntity.ok("Id do Carrinho : " + carrinhocriado.getId());

    }


    @PostMapping("/adicionarproduto/{login}")
    public ResponseEntity<?> adicionarItemCarrinho(@PathVariable String login,
                                                          @RequestBody ItemCarrinhoRequest item) {
        String token = tokenService.getToken();
        Carrinho carrinho = carrinhoService.AdicionarCarrinho(login,item,token);


        return ResponseEntity.ok(carrinho);
    }


    @DeleteMapping("/removerproduto/{login}")
    public ResponseEntity<Carrinho> removerItemCarrinho(
            @PathVariable String login,
           @RequestBody Integer idProduto
    )
    {

        String token = tokenService.getToken();
        Carrinho carrinho = carrinhoService.removerItemCarrinho(login,idProduto,token);
        return ResponseEntity.ok(carrinho);
    }


    @PostMapping("/finalizarcompra/{login}")
    public ResponseEntity<Carrinho> finalizarCarrinho(@PathVariable String login,
                                                      @RequestBody PagamentoCarrinho pagamentoCarrinho) {

        String token = tokenService.getToken();
        Carrinho carrinhoFinalizado = carrinhoService.efetuandoCompraDoCarrrinho(login,pagamentoCarrinho,token);
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

    @GetMapping("/listarPorlogin/{login}")
    public ResponseEntity<Carrinho> listarCarrinhoPorLogin(String login){

        return  ResponseEntity.ok(carrinhoService.listarCarrinhoPorId(login));

    }

}