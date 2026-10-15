package com.fiap.mercadoexpressmensageria.service;

import com.fiap.mercadoexpressmensageria.model.EmailEnviado;
import com.fiap.mercadoexpressmensageria.model.Pedido;
import com.fiap.mercadoexpressmensageria.repository.EmailEnviadoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailEnviadoRepository emailEnviadoRepository;

    @Value("${app.email.remetente}")
    private String remetente;

    public EmailService(JavaMailSender mailSender, EmailEnviadoRepository emailEnviadoRepository) {
        this.mailSender = mailSender;
        this.emailEnviadoRepository = emailEnviadoRepository;
    }

    public void enviarConfirmacaoPagamento(Pedido pedido) {
        String assunto = "Mercado Express - Confirmação de pagamento do pedido #" + pedido.getId();
        String corpo = "Olá, " + pedido.getClienteNome() + "!\n\n"
                + "Seu pagamento do pedido #" + pedido.getId() + " no valor de R$ " + pedido.getValorTotal()
                + " foi confirmado pelo Banco Tranquilo.\n\n"
                + "Obrigado por comprar no Mercado Express.";

        EmailEnviado registro = EmailEnviado.builder()
                .pedidoId(pedido.getId())
                .destinatario(pedido.getClienteEmail())
                .assunto(assunto)
                .enviadoEm(LocalDateTime.now())
                .build();

        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(remetente);
            mensagem.setTo(pedido.getClienteEmail());
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);
            mailSender.send(mensagem);

            registro.setSucesso(true);
        } catch (Exception ex) {
            registro.setSucesso(false);
            registro.setErro(ex.getMessage());
        }

        emailEnviadoRepository.save(registro);
    }

}
