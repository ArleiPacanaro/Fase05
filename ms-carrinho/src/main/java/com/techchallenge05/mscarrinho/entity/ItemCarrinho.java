package com.techchallenge05.mscarrinho.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrinho {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer quantidade;
    private double preco;


}