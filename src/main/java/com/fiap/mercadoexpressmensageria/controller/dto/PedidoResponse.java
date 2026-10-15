package com.fiap.mercadoexpressmensageria.controller.dto;

import com.fiap.mercadoexpressmensageria.model.Pedido;
import com.fiap.mercadoexpressmensageria.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

    private Long id;
    private String clienteNome;
    private String clienteEmail;
    private BigDecimal valorTotal;
    private StatusPedido status;

    public static PedidoResponse de(Pedido pedido) {
        return PedidoResponse.builder()
                .id(pedido.getId())
                .clienteNome(pedido.getClienteNome())
                .clienteEmail(pedido.getClienteEmail())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus())
                .build();
    }

}
