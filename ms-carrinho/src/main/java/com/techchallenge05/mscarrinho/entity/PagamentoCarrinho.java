package com.techchallenge05.mscarrinho.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoCarrinho {

    private String Cartao;
    private String Validade;
    private String Bandeira;
}
