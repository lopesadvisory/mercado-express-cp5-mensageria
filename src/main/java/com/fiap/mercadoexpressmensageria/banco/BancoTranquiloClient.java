package com.fiap.mercadoexpressmensageria.banco;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class BancoTranquiloClient {

    private final RestTemplate restTemplate;

    @Value("${banco.tranquilo.url}")
    private String bancoTranquiloUrl;

    public BancoTranquiloClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RespostaBancoTranquilo solicitarPagamento(Long pedidoId, BigDecimal valor, String numeroCartao) {
        SolicitacaoBancoTranquilo solicitacao = SolicitacaoBancoTranquilo.builder()
                .pedidoId(pedidoId)
                .valor(valor)
                .numeroCartao(numeroCartao)
                .build();

        return restTemplate.postForObject(bancoTranquiloUrl, solicitacao, RespostaBancoTranquilo.class);
    }

}
