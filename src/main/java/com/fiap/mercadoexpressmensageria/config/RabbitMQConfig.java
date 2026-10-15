package com.fiap.mercadoexpressmensageria.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "mercadoexpress.pagamentos";

    public static final String FILA_SOLICITACAO = "fila.solicitacao.pagamento";
    public static final String ROTA_SOLICITACAO = "pagamento.solicitar";

    public static final String FILA_RESPOSTA = "fila.resposta.pagamento";
    public static final String ROTA_RESPOSTA = "pagamento.resposta";

    @Bean
    public DirectExchange pagamentosExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue filaSolicitacaoPagamento() {
        return new Queue(FILA_SOLICITACAO, true);
    }

    @Bean
    public Queue filaRespostaPagamento() {
        return new Queue(FILA_RESPOSTA, true);
    }

    @Bean
    public Binding bindingSolicitacao(Queue filaSolicitacaoPagamento, DirectExchange pagamentosExchange) {
        return BindingBuilder.bind(filaSolicitacaoPagamento).to(pagamentosExchange).with(ROTA_SOLICITACAO);
    }

    @Bean
    public Binding bindingResposta(Queue filaRespostaPagamento, DirectExchange pagamentosExchange) {
        return BindingBuilder.bind(filaRespostaPagamento).to(pagamentosExchange).with(ROTA_RESPOSTA);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

}
