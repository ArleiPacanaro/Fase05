package com.techchallenge05.mscarrinho.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidade para persistir carrinho.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("carrinho")
public class Carrinho {

  @Id
  private String id;
  private String loginCliente;
  private List<ItemCarrinho> itensPedido;
  private LocalDateTime dataCompra;
  private double valorTotal;
  private boolean status;
  private PagamentoCarrinho pagamento;

}
