package com.fiap.mercadoexpressmensageria.messaging;

import com.fiap.mercadoexpressmensageria.config.RabbitMQConfig;
import com.fiap.mercadoexpressmensageria.model.Pedido;
import com.fiap.mercadoexpressmensageria.model.StatusPedido;
import com.fiap.mercadoexpressmensageria.repository.PedidoRepository;
import com.fiap.mercadoexpressmensageria.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consome a resposta do pagamento, atualiza o pedido e, se confirmado, envia o e-mail
 * de confirmação para o comprador.
 */
@Component
public class RespostaPagamentoListener {

    private final PedidoRepository pedidoRepository;
    private final EmailService emailService;

    public RespostaPagamentoListener(PedidoRepository pedidoRepository, EmailService emailService) {
        this.pedidoRepository = pedidoRepository;
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_RESPOSTA)
    public void receber(RespostaPagamentoMessage resposta) {
        Pedido pedido = pedidoRepository.findById(resposta.getPedidoId())
                .orElseThrow(() -> new IllegalStateException("Pedido não encontrado: " + resposta.getPedidoId()));

        pedido.setStatus(resposta.isAprovado() ? StatusPedido.CONFIRMADO : StatusPedido.RECUSADO);
        pedidoRepository.save(pedido);

        if (resposta.isAprovado()) {
            emailService.enviarConfirmacaoPagamento(pedido);
        }
    }

}
