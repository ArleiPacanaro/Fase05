package com.techchallenge05.mscarrinho.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade para dto pagamento carrinho.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoCarrinho {

  private String cartao;
  private String validade;
  private String bandeira;
  private String nome;
  private String cvv;
  private Integer qtdParcelas;
}
