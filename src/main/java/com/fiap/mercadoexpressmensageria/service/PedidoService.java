package com.fiap.mercadoexpressmensageria.service;

import com.fiap.mercadoexpressmensageria.controller.dto.ItemPedidoRequest;
import com.fiap.mercadoexpressmensageria.messaging.PagamentoProducer;
import com.fiap.mercadoexpressmensageria.messaging.SolicitacaoPagamentoMessage;
import com.fiap.mercadoexpressmensageria.model.ItemPedido;
import com.fiap.mercadoexpressmensageria.model.Pedido;
import com.fiap.mercadoexpressmensageria.model.Produto;
import com.fiap.mercadoexpressmensageria.model.StatusPedido;
import com.fiap.mercadoexpressmensageria.repository.PedidoRepository;
import com.fiap.mercadoexpressmensageria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PagamentoProducer pagamentoProducer;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, PagamentoProducer pagamentoProducer) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pagamentoProducer = pagamentoProducer;
    }

    @Transactional
    public Pedido finalizarCompra(String clienteNome, String clienteEmail, String numeroCartao, List<ItemPedidoRequest> itensSolicitados) {
        Pedido pedido = Pedido.builder()
                .clienteNome(clienteNome)
                .clienteEmail(clienteEmail)
                .status(StatusPedido.PENDENTE)
                .valorTotal(BigDecimal.ZERO)
                .criadoEm(LocalDateTime.now())
                .build();

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedidoRequest itemSolicitado : itensSolicitados) {
            if (itemSolicitado.getQuantidade() == null || itemSolicitado.getQuantidade() <= 0) {
                continue;
            }

            Produto produto = produtoRepository.findById(itemSolicitado.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemSolicitado.getProdutoId()));

            int quantidade = itemSolicitado.getQuantidade();
            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
            valorTotal = valorTotal.add(subtotal);

            ItemPedido item = ItemPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(quantidade)
                    .precoUnitario(produto.getPreco())
                    .build();
            pedido.getItens().add(item);

            produto.setEstoque(Math.max(0, produto.getEstoque() - quantidade));
            produtoRepository.save(produto);
        }

        if (pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos um produto para finalizar a compra.");
        }

        pedido.setValorTotal(valorTotal);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        pagamentoProducer.publicarSolicitacao(SolicitacaoPagamentoMessage.builder()
                .pedidoId(pedidoSalvo.getId())
                .valorTotal(pedidoSalvo.getValorTotal())
                .clienteNome(pedidoSalvo.getClienteNome())
                .clienteEmail(pedidoSalvo.getClienteEmail())
                .numeroCartao(numeroCartao)
                .build());

        return pedidoSalvo;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAllByOrderByCriadoEmDesc();
    }

}
