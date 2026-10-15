package com.fiap.mercadoexpressmensageria.service;

import com.fiap.mercadoexpressmensageria.model.Produto;
import com.fiap.mercadoexpressmensageria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

}
