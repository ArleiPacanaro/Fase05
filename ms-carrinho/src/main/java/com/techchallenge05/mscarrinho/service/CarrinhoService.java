package com.techchallenge05.mscarrinho.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.entity.ItemCarrinho;
import com.techchallenge05.mscarrinho.entity.PagamentoCarrinho;
import com.techchallenge05.mscarrinho.repository.CarrinhoRepository;
import com.techchallenge05.mscarrinho.request.ItemCarrinhoRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CarrinhoService {

    private CarrinhoRepository carrinhoRepository;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public Carrinho criarCarrinho(String login,String authorizationHeader){

        Carrinho carrinho = new Carrinho();
        carrinho.setLoginCliente(login);
        carrinho.setStatus(true);
        carrinho.setDataCompra(LocalDateTime.now());

        carrinhoRepository.findByLoginCliente(login,true).ifPresent(carrinhoRepository::delete);
        return carrinhoRepository.save(carrinho);

    }

    public Carrinho AdicionarCarrinho(String login, ItemCarrinhoRequest item,String authorizationHeader) {

        try {
            Carrinho carrinhoAberto = carrinhoRepository.findByLoginCliente(login, true)
                    .orElseThrow();


            if (carrinhoAberto == null) {

                throw new NoSuchElementException("Carrinho não existe");

            }

            if(verificarDisponibilidadeProdutos(item,authorizationHeader))
            {

                ItemCarrinho itemCarrinho
                                = RecuperaItem(item,authorizationHeader);

                carrinhoAberto.addProduto(itemCarrinho);
            }
            else {

                throw new NoSuchElementException("Produto não disponível em quantidade suficiente");
            }

            carrinhoAberto.setValorTotal(calcularValorTotal(carrinhoAberto.getItensPedido(),authorizationHeader));
            return carrinhoRepository.save(carrinhoAberto);
        }
        catch (Exception e){

            e.getMessage();
        }

        return null;

    }


    public Carrinho removerItemCarrinho(String login, Integer idProduto,String authorizationHeader) {

        // Atualizar Estoque

        Carrinho carrinhoAberto = carrinhoRepository.findByLoginCliente(login, true)
                .orElseThrow();

        if (carrinhoAberto == null) {

            throw new NoSuchElementException("Carrinho não existe");

        }

        Optional<ItemCarrinho> itemCarrinhoOptional = carrinhoAberto.getItensPedido().stream()
                .filter(item -> item.getId().equals(idProduto))
                .findFirst();

        if (itemCarrinhoOptional.isPresent()) {

            carrinhoAberto.getItensPedido().remove(itemCarrinhoOptional.get());
            var qtd = itemCarrinhoOptional.get().getQuantidade() * -1;
            itemCarrinhoOptional.get().setQuantidade(qtd);
            atualizarEstoqueProdutos(itemCarrinhoOptional.get(),authorizationHeader);

        } else {

            throw new NoSuchElementException("Item não existe no Carrinho");
        }


        // Atualizar Carrinho
        carrinhoAberto.setValorTotal(calcularValorTotal(carrinhoAberto.getItensPedido(),authorizationHeader));
        return carrinhoRepository.save(carrinhoAberto);
    }

    public Carrinho efetuandoCompraDoCarrrinho(String login, PagamentoCarrinho pagamentoCarrinho,String authorizationHeader) {

        Carrinho carrinhoAberto = carrinhoRepository.findByLoginCliente(login, true)
                .orElseThrow();

        if (carrinhoAberto == null) {

            throw new NoSuchElementException("Carrinho não existe");

        }

        carrinhoAberto.setDataCompra(LocalDateTime.now());
        carrinhoAberto.setStatus(false);
        carrinhoAberto.setPagamento(pagamentoCarrinho);

        return carrinhoRepository.save(carrinhoAberto);

    }

    public List<Carrinho> listarCarrinhos(){
        return carrinhoRepository.findAll();
    }

    public Carrinho listarCarrinhoPorId(String pedidoId) {
        return carrinhoRepository.findById(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado"));
    }

    public Carrinho listarCArrinhoPorLoginCliente(String login,boolean status) {
        return carrinhoRepository.findByLoginCliente(login, status)
                .orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado"));

    }

    private boolean verificarDisponibilidadeProdutos(ItemCarrinhoRequest itemCarrinhoPedido,String authorizationHeader) {

        ItemCarrinho  itemCarrinho =
                    RecuperaItem(itemCarrinhoPedido,authorizationHeader);

        if (itemCarrinho.getQuantidade() < itemCarrinhoPedido.getQuantidade()) {
            return false; // Produto não disponível em quantidade suficiente
        }
        return true; // Todos os produtos estão disponíveis



    }


    private void atualizarEstoqueProdutos(ItemCarrinho itemCarrinhoPedido,String authorizationHeader) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Integer produtoID = itemCarrinhoPedido.getId();
        Integer quantidade = itemCarrinhoPedido.getQuantidade();


        // aqui dando erro

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/produto/atualizar/estoque/" + produtoID.toString() + "/" + quantidade.toString() ,
                HttpMethod.PUT,
                entity,
                String.class
        );
    }


    private double calcularValorTotal(List<ItemCarrinho> itensPedido,String authorizationHeader) {


        double valorTotal = 0.0;

        for (ItemCarrinho itemPedido : itensPedido) {


            valorTotal += itemPedido.getPreco() * itemPedido.getQuantidade();



        }

        return valorTotal;
    }


    private ItemCarrinho RecuperaItem(ItemCarrinhoRequest itemCarrinhoPedido, String authorizationHeader) {


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);


        Integer Id = itemCarrinhoPedido.getId();
        int quantidade = itemCarrinhoPedido.getQuantidade();


        ItemCarrinho itemCarrinho
                        = new ItemCarrinho();


        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/produto/" + Id.toString(),
                HttpMethod.GET,
                entity,
                String.class

        );

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NoSuchElementException("Produto não encontrado");
        } else if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode produtoJson = objectMapper.readTree(response.getBody());

                itemCarrinho.setId(produtoJson.get("id").asInt());
                itemCarrinho.setNome(produtoJson.get("nome").asText());
                itemCarrinho.setDescricao(produtoJson.get("descricao").asText());
                itemCarrinho.setPreco(produtoJson.get("preco").asDouble());
                itemCarrinho.setQuantidade(produtoJson.get("quantidade_estoque").asInt());


            } catch (IOException e) {
                e.getMessage();
            }
        }

        return itemCarrinho;
    }



}