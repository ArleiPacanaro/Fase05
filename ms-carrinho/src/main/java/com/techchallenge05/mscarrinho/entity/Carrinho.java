package com.techchallenge05.mscarrinho.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

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
    /*
    true = aberto
    false = fechado
     */
    private boolean status;
    private PagamentoCarrinho pagamento;

    public void addProduto(ItemCarrinho itemCarrinho) {
        this.itensPedido.add(itemCarrinho);
    }



}
