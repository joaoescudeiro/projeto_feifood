/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Escudeiro
 */
public class PedidoAtual {
    private static Pedido pedidoEmAndamento;
    private static List<PedidoItem> itensDoPedido;
    private static Usuario usuarioLogado;

    public static void iniciarNovoPedido(Usuario usuario) {
        usuarioLogado = usuario;
        pedidoEmAndamento = new Pedido();
        pedidoEmAndamento.setUsuario(usuario);
        pedidoEmAndamento.setTotal(0.0);
        pedidoEmAndamento.setAvaliacao(0);
        itensDoPedido = new ArrayList<>();
    }

    public static Pedido getPedidoEmAndamento() {
        if (pedidoEmAndamento == null) {
            throw new IllegalStateException("Nenhum pedido em andamento. O usuário deve estar logado.");
        }
        return pedidoEmAndamento;
    }

    public static List<PedidoItem> getItensDoPedido() {
        if (itensDoPedido == null) {
            itensDoPedido = new ArrayList<>();
        }
        return itensDoPedido;
    }

    public static void adicionarItem(Alimento alimento, int quantidade) {
        for (PedidoItem item : itensDoPedido) {
            if (item.getAlimento().getId() == alimento.getId()) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                item.setSubtotal(item.getQuantidade() * alimento.getPreco());
                recalcularTotal();
                return;
            }
        }

        PedidoItem novoItem = new PedidoItem();
        novoItem.setAlimento(alimento);
        novoItem.setQuantidade(quantidade);
        novoItem.setSubtotal(alimento.getPreco() * quantidade);
        itensDoPedido.add(novoItem);
        recalcularTotal();
    }

    public static void removerItem(Alimento alimento) {
        itensDoPedido.removeIf(item -> item.getAlimento().getId() == alimento.getId());
        recalcularTotal();
    }
    
    public static void limparItens() {
        itensDoPedido.clear();
        recalcularTotal();
    }

     private static void recalcularTotal() {
        double total = 0.0;
        double impostoTotal = 0.0;
        
        for (PedidoItem item : itensDoPedido) {
            total += item.getSubtotal();
            
            if (item.getAlimento() instanceof Bebida) {
                Bebida bebida = (Bebida) item.getAlimento();
                if (bebida.isAlcoolica()) {
                    impostoTotal += bebida.calcularImpostoAlcool() * item.getQuantidade();
                }
            }
        }
        
        total += impostoTotal;
        pedidoEmAndamento.setTotal(total);
    }
    
    public static boolean temItens() {
        return itensDoPedido != null && !itensDoPedido.isEmpty();
    }
}
