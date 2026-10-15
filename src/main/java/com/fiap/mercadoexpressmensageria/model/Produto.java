package com.fiap.mercadoexpressmensageria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "TDS_MSG_TB_MERCADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "PRECO", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "CUSTO", nullable = false, precision = 10, scale = 2)
    private BigDecimal custo;

    @Column(name = "ESTOQUE", nullable = false)
    private Integer estoque;

}
