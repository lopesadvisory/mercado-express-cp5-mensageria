package com.fiap.mercadoexpressmensageria.banco;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Simula a API do Banco Tranquilo, a instituição parceira fictícia descrita no enunciado,
 * que autoriza ou recusa os pagamentos do Mercado Express.
 */
@RestController
@RequestMapping("/banco-tranquilo")
public class BancoTranquiloController {

    @PostMapping("/pagamentos")
    public RespostaBancoTranquilo autorizar(@Valid @RequestBody SolicitacaoBancoTranquilo solicitacao) {
        boolean recusado = solicitacao.getNumeroCartao() != null && solicitacao.getNumeroCartao().endsWith("0000");

        if (recusado) {
            return RespostaBancoTranquilo.builder()
                    .aprovado(false)
                    .motivo("Cartão recusado pelo Banco Tranquilo")
                    .build();
        }

        return RespostaBancoTranquilo.builder()
                .aprovado(true)
                .codigoAutorizacao(UUID.randomUUID().toString())
                .motivo("Pagamento autorizado")
                .build();
    }

}
