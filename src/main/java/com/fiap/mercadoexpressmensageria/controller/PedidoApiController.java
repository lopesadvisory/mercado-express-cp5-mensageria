package com.fiap.mercadoexpressmensageria.controller;

import com.fiap.mercadoexpressmensageria.controller.dto.NovoPedidoRequest;
import com.fiap.mercadoexpressmensageria.controller.dto.PedidoResponse;
import com.fiap.mercadoexpressmensageria.model.Pedido;
import com.fiap.mercadoexpressmensageria.repository.PedidoRepository;
import com.fiap.mercadoexpressmensageria.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * API REST do Back End descrito no diagrama do enunciado: recebe a solicitação de compra
 * (vinda do front-end Web, de um app mobile ou de uma ferramenta como o Postman), cria o
 * pedido e publica a solicitação de pagamento na fila do RabbitMQ.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoApiController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public PedidoApiController(PedidoService pedidoService, PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(@Valid @RequestBody NovoPedidoRequest requisicao) {
        Pedido pedido = pedidoService.finalizarCompra(
                requisicao.getClienteNome(),
                requisicao.getClienteEmail(),
                requisicao.getNumeroCartao(),
                requisicao.getItens()
        );
        return PedidoResponse.de(pedido);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));
        return PedidoResponse.de(pedido);
    }

}
