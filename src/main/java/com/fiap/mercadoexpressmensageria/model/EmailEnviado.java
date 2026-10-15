package com.fiap.mercadoexpressmensageria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TDS_MSG_TB_EMAIL_ENVIADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailEnviado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PEDIDO_ID", nullable = false)
    private Long pedidoId;

    @Column(name = "DESTINATARIO", nullable = false, length = 150)
    private String destinatario;

    @Column(name = "ASSUNTO", nullable = false, length = 200)
    private String assunto;

    @Column(name = "SUCESSO", nullable = false)
    private boolean sucesso;

    @Column(name = "ERRO", length = 500)
    private String erro;

    @Column(name = "ENVIADO_EM", nullable = false)
    private LocalDateTime enviadoEm;

}
