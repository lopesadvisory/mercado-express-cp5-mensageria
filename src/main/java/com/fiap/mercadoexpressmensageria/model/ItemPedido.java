package com.fiap.mercadoexpressmensageria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "TDS_MSG_TB_ITEM_PEDIDO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PEDIDO_ID", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "PRODUTO_ID", nullable = false)
    private Produto produto;

    @Column(name = "QUANTIDADE", nullable = false)
    private Integer quantidade;

    @Column(name = "PRECO_UNITARIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

}
