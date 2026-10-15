package com.fiap.mercadoexpressmensageria.repository;

import com.fiap.mercadoexpressmensageria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
