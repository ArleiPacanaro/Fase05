package com.techchallenge05.mscarrinho.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge05.mscarrinho.entity.Carrinho;
import com.techchallenge05.mscarrinho.entity.ItemCarrinho;
import com.techchallenge05.mscarrinho.entity.PagamentoCarrinho;
import com.techchallenge05.mscarrinho.repository.CarrinhoRepository;
import com.techchallenge05.mscarrinho.request.ItemCarrinhoRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Classe service para o carrinho.
 */

@Service
@AllArgsConstructor
public class CarrinhoService {

  private CarrinhoRepository carrinhoRepository;
  private RestTemplate restTemplate;
  private ObjectMapper objectMapper;

  /**
  * Metodo service para cria o carrinho.
  */
  public Carrinho criarCarrinho(String login, String authorizationHeader) {

    Carrinho carrinho = new Carrinho();
    carrinho.setLoginCliente(login);
    carrinho.setStatus(true);
    carrinho.setDataCompra(LocalDateTime.now());

    carrinhoRepository.findByLoginCliente(login, true).ifPresent(carrinhoRepository::delete);
    return carrinhoRepository.save(carrinho);

  }

  /**
  * Metodo service para adicionar produto ao carrinho.
  */
  public Carrinho AdicionarCarrinho(String login,
                                    ItemCarrinhoRequest item, String authorizationHeader) {

    try {
      Carrinho carrinhoAberto = carrinhoRepository.findByLoginCliente(login, true)
                    .orElseThrow();


      if (carrinhoAberto == null) {

        throw new NoSuchElementException("Carrinho não existe");

      }
      var disponilidade = verificarDisponibilidadeProdutos(item,  authorizationHeader);
      if (disponilidade == true) {
        ItemCarrinho itemCarrinho
                                = RecuperaItem(item, authorizationHeader);

        itemCarrinho.setQuantidade(item.getQuantidade());

        List<ItemCarrinho> itemCarrinhos = new ArrayList<>();

        if (carrinhoAberto.getItensPedido() != null) {
          carrinhoAberto.getItensPedido().forEach(i -> itemCarrinhos.add(i));
        }

        itemCarrinhos.add(itemCarrinho);
        carrinhoAberto.setItensPedido(itemCarrinhos);
        atualizarEstoqueProdutos(itemCarrinho, authorizationHeader);
      } else {
        throw new NoSuchElementException("Produto não disponível em quantidade suficiente");
      }

      var valorTotal = calcularValorTotal(carrinhoAberto.getItensPedido(), authorizationHeader);
      carrinhoAberto.setValorTotal(valorTotal);

      Carrinho carrinhoGravado = carrinhoRepository.save(carrinhoAberto);
      return carrinhoGravado;
    } catch (Exception e) {
      throw new NoSuchElementException(e.getMessage());
    }

  }

  /**
  * Metodo service para remover produto ao carrinho.
  */
  public Carrinho removerItemCarrinho(String login, Integer idProduto, String authorizationHeader) {



    Carrinho carrinhoAberto = carrinhoRepository.findByLoginCliente(login, true)
                .orElseThrow();

    if (carrinhoAberto == null) {
      throw new NoSuchElementException("Carrinho aberto não existe");
    }

    List<ItemCarrinho> itemCarrinhos =  new ArrayList<>();
    itemCarrinhos = carrinhoAberto.getItensPedido();

    for (int i = 0; i < itemCarrinhos.size(); i++) {
      if (itemCarrinhos.get(i).getId() == idProduto) {
        atualizarEstoqueProdutos(itemCarrinhos.get(i), authorizationHeader);
        itemCarrinhos.remove(i);
      }
    }

    carrinhoAberto.setItensPedido(itemCarrinhos);

    var valorTotal = calcularValorTotal(carrinhoAberto.getItensPedido(), authorizationHeader);
    carrinhoAberto.setValorTotal(valorTotal);

    Carrinho carrinhoGravado = carrinhoRepository.save(carrinhoAberto);
    return carrinhoGravado;
  }

  /**
  * Metodo service para remover produto ao carrinho.
  */
  public Carrinho efetuandoCompraDoCarrrinho(String login,
                                             PagamentoCarrinho pagamentoCarrinho,
                                             String authorizationHeader) {

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

  public List<Carrinho> listarCarrinhos() {
    return carrinhoRepository.findAll();
  }

  public Carrinho listarCarrinhoPorId(String pedidoId) {
    return carrinhoRepository.findById(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado"));
  }

  public Carrinho listarCArrinhoPorLoginCliente(String login, boolean status) {
    return carrinhoRepository.findByLoginCliente(login, status)
                .orElseThrow(() -> new NoSuchElementException("Carrinho não encontrado"));

  }

  /**
  * Metodo service para verificar se produto existe no estoque, chamado pelo adicionar carrinho.
  */
  private boolean verificarDisponibilidadeProdutos(ItemCarrinhoRequest itemCarrinhoPedido,
                                                   String authorizationHeader) {

    ItemCarrinho  itemCarrinho =
                    RecuperaItem(itemCarrinhoPedido, authorizationHeader);

    if (itemCarrinho.getQuantidade() < itemCarrinhoPedido.getQuantidade()) {
      return false; // Produto não disponível em quantidade suficiente
    }
    return true; // Todos os produtos estão disponíveis

  }


  /**
  * Metodo service para ajustar produto no estoque, chamado pelo adicionar e remover carrinho.
  */
  private void atualizarEstoqueProdutos(ItemCarrinho itemCarrinhoPedido,
                                        String authorizationHeader) {

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(authorizationHeader);
    HttpEntity<String> entity = new HttpEntity<>(headers);

    Integer produtoID = itemCarrinhoPedido.getId();
    Integer quantidade = itemCarrinhoPedido.getQuantidade();

    ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/produto/atualizar/estoque/{produtoId}/{quantidade}",
                HttpMethod.PUT,
                entity,
                String.class,
                produtoID,
                quantidade
    );

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new NoSuchElementException("erro ao atualizar estoque");
    }
  }


  /**
  * Metodo service para calcular volor total do carrinho, chamado pelo adicionar e remover carrinho.
  */
  private double calcularValorTotal(List<ItemCarrinho> itensPedido, String authorizationHeader) {
    double valorTotal = 0.0;
    for (ItemCarrinho itemPedido : itensPedido) {
      valorTotal += itemPedido.getPreco() * itemPedido.getQuantidade();
    }
    return valorTotal;
  }


  /**
  * Metodo service para recuoerar produto do estoque.
  */
  private ItemCarrinho RecuperaItem(ItemCarrinhoRequest itemCarrinhoPedido,
                                    String authorizationHeader) {


    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(authorizationHeader);
    HttpEntity<String> entity = new HttpEntity<>(headers);

    Integer Id = itemCarrinhoPedido.getId();
    int quantidade = itemCarrinhoPedido.getQuantidade();


    ItemCarrinho itemCarrinho
                        = new ItemCarrinho();
    ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/produto/{produtoId}",
                HttpMethod.GET,
                entity,
                String.class,
                Id.toString()
    );

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new NoSuchElementException("Produto não encontrado");
    } else if (response.getStatusCode().is2xxSuccessful()) {
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