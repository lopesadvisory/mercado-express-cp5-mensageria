package com.fiap.mercadoexpressmensageria.messaging;

import com.fiap.mercadoexpressmensageria.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PagamentoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarSolicitacao(SolicitacaoPagamentoMessage mensagem) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROTA_SOLICITACAO, mensagem);
    }

    public void publicarResposta(RespostaPagamentoMessage mensagem) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROTA_RESPOSTA, mensagem);
    }

}
