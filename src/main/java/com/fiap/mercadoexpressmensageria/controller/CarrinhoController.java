package com.fiap.mercadoexpressmensageria.controller;

import com.fiap.mercadoexpressmensageria.controller.dto.ItemPedidoRequest;
import com.fiap.mercadoexpressmensageria.model.Pedido;
import com.fiap.mercadoexpressmensageria.repository.PedidoRepository;
import com.fiap.mercadoexpressmensageria.service.PedidoService;
import com.fiap.mercadoexpressmensageria.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CarrinhoController {

    private final ProdutoService produtoService;
    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public CarrinhoController(ProdutoService produtoService, PedidoService pedidoService, PedidoRepository pedidoRepository) {
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping("/")
    public String loja(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "loja";
    }

    @PostMapping("/comprar")
    public String comprar(@RequestParam String clienteNome,
                           @RequestParam String clienteEmail,
                           @RequestParam String numeroCartao,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {
        List<ItemPedidoRequest> itens = new ArrayList<>();
        request.getParameterMap().forEach((chave, valores) -> {
            if (chave.startsWith("qtd_") && valores.length > 0 && !valores[0].isBlank()) {
                Long produtoId = Long.valueOf(chave.substring(4));
                Integer quantidade = Integer.valueOf(valores[0]);
                if (quantidade > 0) {
                    itens.add(ItemPedidoRequest.builder().produtoId(produtoId).quantidade(quantidade).build());
                }
            }
        });

        try {
            Pedido pedido = pedidoService.finalizarCompra(clienteNome, clienteEmail, numeroCartao, itens);
            return "redirect:/pedidos/" + pedido.getId();
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/pedidos/{id}")
    public String acompanharPedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));
        model.addAttribute("pedido", pedido);
        return "pedido";
    }

}
