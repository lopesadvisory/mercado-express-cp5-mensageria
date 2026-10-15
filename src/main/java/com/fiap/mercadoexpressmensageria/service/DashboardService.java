package com.fiap.mercadoexpressmensageria.service;

import com.fiap.mercadoexpressmensageria.model.ItemPedido;
import com.fiap.mercadoexpressmensageria.model.Pedido;
import com.fiap.mercadoexpressmensageria.model.Produto;
import com.fiap.mercadoexpressmensageria.model.StatusPedido;
import com.fiap.mercadoexpressmensageria.repository.EmailEnviadoRepository;
import com.fiap.mercadoexpressmensageria.repository.PedidoRepository;
import com.fiap.mercadoexpressmensageria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final EmailEnviadoRepository emailEnviadoRepository;

    public DashboardService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, EmailEnviadoRepository emailEnviadoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.emailEnviadoRepository = emailEnviadoRepository;
    }

    public Map<String, Object> montarResumo() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();

        long confirmados = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.CONFIRMADO).count();
        long recusados = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.RECUSADO).count();
        long pendentes = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.PENDENTE).count();

        BigDecimal receitaTotal = pedidos.stream()
                .filter(p -> p.getStatus() == StatusPedido.CONFIRMADO)
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal lucroTotal = BigDecimal.ZERO;
        Map<String, Integer> vendasPorProduto = new LinkedHashMap<>();
        for (Produto produto : produtos) {
            vendasPorProduto.put(produto.getNome(), 0);
        }

        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() != StatusPedido.CONFIRMADO) {
                continue;
            }
            for (ItemPedido item : pedido.getItens()) {
                BigDecimal lucroItem = item.getPrecoUnitario()
                        .subtract(item.getProduto().getCusto())
                        .multiply(BigDecimal.valueOf(item.getQuantidade()));
                lucroTotal = lucroTotal.add(lucroItem);

                vendasPorProduto.merge(item.getProduto().getNome(), item.getQuantidade(), Integer::sum);
            }
        }

        Map<String, Integer> estoquePorProduto = new LinkedHashMap<>();
        for (Produto produto : produtos) {
            estoquePorProduto.put(produto.getNome(), produto.getEstoque());
        }

        long emailsEnviados = emailEnviadoRepository.count();

        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("totalPedidos", pedidos.size());
        resumo.put("confirmados", confirmados);
        resumo.put("recusados", recusados);
        resumo.put("pendentes", pendentes);
        resumo.put("receitaTotal", receitaTotal);
        resumo.put("lucroTotal", lucroTotal);
        resumo.put("vendasPorProduto", vendasPorProduto);
        resumo.put("estoquePorProduto", estoquePorProduto);
        resumo.put("emailsEnviados", emailsEnviados);
        resumo.put("pedidos", pedidos);

        return resumo;
    }

}
