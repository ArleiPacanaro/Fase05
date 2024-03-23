package com.techchallenge05.mscarrinho.repository;

import com.techchallenge05.mscarrinho.entity.Carrinho;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarrinhoRepository extends MongoRepository<Carrinho, String> {

   // @Query("{ 'loginCliente' : { $regex: ?0, $options: 'i' } }")
    @Query("{ $and: [{'loginCliente':{$regex:?0}},{'status':{$eq:?1}}] }")
    Optional<Carrinho> findByLoginCliente(String LoginCliente,boolean status);
}
