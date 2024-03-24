package com.techchallenge05.msestoque.repository;

import com.techchallenge05.msestoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface para JPA.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
