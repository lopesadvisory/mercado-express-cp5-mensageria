package com.fiap.mercadoexpressmensageria.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NovoPedidoRequest {

    @NotBlank
    private String clienteNome;

    @NotBlank
    @Email
    private String clienteEmail;

    @NotBlank
    private String numeroCartao;

    @NotEmpty
    @Valid
    private List<ItemPedidoRequest> itens;

}
