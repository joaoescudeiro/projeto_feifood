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
public class Pedido {
    private int id;
    private Usuario usuario;
    private List<PedidoItem> itens;
    private double total;
    private int avaliacao;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    public Pedido(int id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
        this.itens = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<PedidoItem> getItens() { return itens; }
    public void setItens(List<PedidoItem> itens) { this.itens = itens; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getAvaliacao() { return avaliacao; }
    public void setAvaliacao(int avaliacao) { this.avaliacao = avaliacao; }

    public void adicionarItem(PedidoItem item) {
        itens.add(item);
        recalcularTotal();
    }

    public void removerItem(PedidoItem item) {
        itens.remove(item);
        recalcularTotal();
    }

    private void recalcularTotal() {
        total = 0;
        for (PedidoItem item : itens) {
            total += item.getSubtotal();
        }
    }
}
