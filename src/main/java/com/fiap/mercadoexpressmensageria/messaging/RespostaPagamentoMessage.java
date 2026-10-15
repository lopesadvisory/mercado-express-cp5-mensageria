package com.fiap.mercadoexpressmensageria.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespostaPagamentoMessage implements Serializable {

    private Long pedidoId;
    private boolean aprovado;
    private String codigoAutorizacao;
    private String motivo;

}
