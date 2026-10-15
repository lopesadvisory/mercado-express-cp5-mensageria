package com.fiap.mercadoexpressmensageria.config;

import com.fiap.mercadoexpressmensageria.model.Produto;
import com.fiap.mercadoexpressmensageria.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProdutoSeeder implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;

    public ProdutoSeeder(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) {
        if (produtoRepository.count() > 0) {
            return;
        }

        produtoRepository.save(Produto.builder()
                .nome("Detergente Neutro").tipo("Limpeza")
                .preco(new BigDecimal("3.49")).custo(new BigDecimal("1.80")).estoque(50)
                .build());

        produtoRepository.save(Produto.builder()
                .nome("Sabão em Pó 1kg").tipo("Limpeza")
                .preco(new BigDecimal("12.90")).custo(new BigDecimal("7.50")).estoque(30)
                .build());

        produtoRepository.save(Produto.builder()
                .nome("Arroz 5kg").tipo("Mercearia")
                .preco(new BigDecimal("24.90")).custo(new BigDecimal("18.00")).estoque(40)
                .build());

        produtoRepository.save(Produto.builder()
                .nome("Feijão 1kg").tipo("Mercearia")
                .preco(new BigDecimal("8.50")).custo(new BigDecimal("5.70")).estoque(60)
                .build());

        produtoRepository.save(Produto.builder()
                .nome("Meia Esportiva").tipo("Vestuário")
                .preco(new BigDecimal("9.90")).custo(new BigDecimal("4.20")).estoque(25)
                .build());
    }

}
