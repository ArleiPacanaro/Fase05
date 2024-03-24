package com.techchallenge05.mscarrinho.service;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.entity.PagamentoCarrinho;
import com.techchallenge05.mscarrinho.repository.CarrinhoRepository;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PagamentoCarrinhoService {

  private CarrinhoRepository carrinhoRepository;

  /**
  * Metodo service para efetuar compra do carrinho.
  */
  public Carrinho efetuandoCompraDoCarrinho(String login,
                                              PagamentoCarrinho pagamentoCarrinho,
                                              String authorizationHeader) {

    Carrinho carrinhoAberto = carrinhoRepository.findByLoginCliente(login, true)
                .orElseThrow();

    if (carrinhoAberto == null) {
         throw new NoSuchElementException("Carrinho não existe");
    }
    carrinhoAberto.setDataCompra(LocalDateTime.now());
    carrinhoAberto.setStatus(false);
    carrinhoAberto.setPagamento(pagamentoCarrinho);
    return carrinhoRepository.save(carrinhoAberto);

  }
}
