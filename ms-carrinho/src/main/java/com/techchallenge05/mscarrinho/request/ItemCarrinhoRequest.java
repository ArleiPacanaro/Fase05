package com.techchallenge05.mscarrinho.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade para DTO de inclusão de produtos no carrinho.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrinhoRequest {

  @NotNull(message = "ID do Item deve ser informado")
  private Integer id;
  @Positive(message = "Quantidade deve ser maior que zero")
  @NotNull(message = "Quantidade do Item deve ser informada")
  private Integer quantidade;

}
