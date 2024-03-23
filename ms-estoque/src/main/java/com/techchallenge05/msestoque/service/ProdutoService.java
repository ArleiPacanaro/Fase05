package com.techchallenge05.msestoque.service;

import com.techchallenge05.msestoque.repository.ProdutoRepository;
import com.techchallenge05.msestoque.request.ProdutoRequest;
import com.techchallenge05.msestoque.response.ProdutoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ResponseEntity<List<ProdutoResponse>> listarProdutos() {

        var produtos = produtoRepository.findAll();
        var produtosResponse = new ArrayList<ProdutoResponse>();

        produtos.forEach(p -> produtosResponse.add(new ProdutoResponse(p)));

        return ResponseEntity.ok(produtosResponse);
    }

    public ResponseEntity<?> listarUmProduto(Integer produtoId) {

        var produto = produtoRepository.findById(produtoId).orElseThrow();

        if (produto == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");

        return ResponseEntity.ok(new ProdutoResponse(produto));

    }

    public ResponseEntity cadastrarProduto(ProdutoRequest produtoRequest) {

        if(produtoRequest.getId() != null && produtoRepository.existsById(produtoRequest.getId()))
            return ResponseEntity.badRequest().body("Já existe um produto com esse ID!");

        var produto = produtoRepository.save(produtoRequest.toProduto());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProdutoResponse(produto));

    }

    public ResponseEntity atualizarProduto(Integer produtoId, ProdutoRequest produtoRequest) {

        var produtoExistente = produtoRepository.findById(produtoId).orElseThrow();

        if(produtoExistente == null)
            return ResponseEntity.badRequest().body("Não foi encontrado produto com o ID informado!");


        produtoExistente.setNome(produtoRequest.getNome());
        produtoExistente.setDescricao(produtoRequest.getDescricao());
        produtoExistente.setQuantidade_estoque(produtoRequest.getQuantidade_estoque());
        produtoExistente.setPreco(produtoRequest.getPreco());

        produtoRepository.save(produtoExistente);
        return ResponseEntity.ok().body(new ProdutoResponse(produtoExistente));

    }

    public ResponseEntity excluirProduto(Integer produtoId) {

        var produtoExistente = produtoRepository.findById(produtoId);

        if(produtoExistente.isEmpty())
            return ResponseEntity.badRequest().body("Não foi encontrado produto com o ID informado!");

        produtoRepository.delete(produtoExistente.get());
        return ResponseEntity.ok().build();

    }

    public ResponseEntity atualizarEstoque(Integer produtoId, int quantidade) {

        var produtoExistente = produtoRepository.findById(produtoId).orElseThrow();

        if(produtoExistente == null)
            return ResponseEntity.badRequest().body("Não foi encontrado produto com o ID informado!");


        if(quantidade > 0)
            produtoExistente.setQuantidade_estoque(produtoExistente.getQuantidade_estoque() - quantidade);
        else
            produtoExistente.setQuantidade_estoque(produtoExistente.getQuantidade_estoque() + (quantidade * -1));


        return ResponseEntity.ok().body(produtoRepository.save(produtoExistente));

    }

}