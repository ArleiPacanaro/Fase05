package com.techchallenge05.msestoque.request;

import com.techchallenge05.msestoque.entity.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest {

    @NotNull(message="Campo ID Obrigatorio")
    private Integer id;
    @NotBlank(message = "Campo Nome Obrigatorio")
    private String nome;
    @NotBlank(message = "Campo Descricao Obrigatorio")
    private String descricao;
    @NotBlank(message = "Campo Quantidade Obrigatorio")
    @PositiveOrZero(message = "Campo deve igual ou maior que zero" )
    private int quantidade_estoque;
    @PositiveOrZero(message = "Campo deve igual ou maior que zero" )
    private double preco;


    public Produto toProduto(){

        return new Produto(
                this.id,
                this.nome,
                this.descricao,
                this.quantidade_estoque,
                this.preco
        );

    }

}
