package com.techchallenge05.mscarrinho.repository;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Interface para Spring Data Mongo.
 */
public interface CarrinhoRepository extends MongoRepository<Carrinho, String> {



  @Query("{ $and: [ { 'loginCliente': { $regex: ?0, $options: 'i' } }, { 'status': { $eq: ?1 } } ] }")
  Optional<Carrinho> findByLoginCliente(String LoginCliente, boolean status);
}
