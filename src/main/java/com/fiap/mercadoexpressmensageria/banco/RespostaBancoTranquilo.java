package com.fiap.mercadoexpressmensageria.banco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespostaBancoTranquilo {

    private boolean aprovado;
    private String codigoAutorizacao;
    private String motivo;

}
