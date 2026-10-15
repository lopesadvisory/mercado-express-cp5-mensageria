package com.fiap.mercadoexpressmensageria.messaging;

import com.fiap.mercadoexpressmensageria.banco.BancoTranquiloClient;
import com.fiap.mercadoexpressmensageria.banco.RespostaBancoTranquilo;
import com.fiap.mercadoexpressmensageria.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Representa o serviço de "Integração de Pagamentos" do diagrama: consome a solicitação de
 * compra, chama a API do Banco Tranquilo e publica a resposta de volta na fila.
 */
@Component
public class SolicitacaoPagamentoListener {

    private final BancoTranquiloClient bancoTranquiloClient;
    private final PagamentoProducer pagamentoProducer;

    public SolicitacaoPagamentoListener(BancoTranquiloClient bancoTranquiloClient, PagamentoProducer pagamentoProducer) {
        this.bancoTranquiloClient = bancoTranquiloClient;
        this.pagamentoProducer = pagamentoProducer;
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_SOLICITACAO)
    public void receber(SolicitacaoPagamentoMessage solicitacao) {
        RespostaBancoTranquilo respostaBanco = bancoTranquiloClient.solicitarPagamento(
                solicitacao.getPedidoId(),
                solicitacao.getValorTotal(),
                solicitacao.getNumeroCartao()
        );

        RespostaPagamentoMessage resposta = RespostaPagamentoMessage.builder()
                .pedidoId(solicitacao.getPedidoId())
                .aprovado(respostaBanco.isAprovado())
                .codigoAutorizacao(respostaBanco.getCodigoAutorizacao())
                .motivo(respostaBanco.getMotivo())
                .build();

        pagamentoProducer.publicarResposta(resposta);
    }

}
