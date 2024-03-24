package com.techchallenge05.mscarrinho.response;

import com.techchallenge05.mscarrinho.entity.ItemCarrinho;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade de dto response para Carrinho.
 */
public class CarrinhoResponse {

  private String id;
  private String nomeCliente;
  private List<ItemCarrinho> itensPedido;
  private double valorTotal;
  private LocalDateTime dataCompra;

}
