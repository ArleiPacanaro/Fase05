package com.techchallenge05.msestoque.response;

import com.techchallenge05.msestoque.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * DTO para reponse de cadastro de Produtos.
 */
@Getter
@AllArgsConstructor
public class ProdutoResponse {

  private Integer id;
  private String nome;
  private String descricao;
  private int quantidade_estoque;
  private double preco;

  /**
   * Construtor com parameto da classe entity.
   */
  public ProdutoResponse(Produto p) {
    this.id         = p.getId();
    this.nome       = p.getNome();
    this.descricao  = p.getDescricao();
    this.preco      = p.getPreco();
    this.quantidade_estoque = p.getQuantidade_estoque();
  }
}
