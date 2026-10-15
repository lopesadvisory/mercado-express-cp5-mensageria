package com.fiap.mercadoexpressmensageria.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitacaoPagamentoMessage implements Serializable {

    private Long pedidoId;
    private BigDecimal valorTotal;
    private String clienteNome;
    private String clienteEmail;
    private String numeroCartao;

}
