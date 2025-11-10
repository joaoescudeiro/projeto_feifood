/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Escudeiro
 */
public class PedidoItem {
    private int id;
    private Alimento alimento;
    private int quantidade;
    private double subtotal;

    public PedidoItem() {}

    public PedidoItem(Alimento alimento, int quantidade) {
        this.alimento = alimento;
        this.quantidade = quantidade;
        if (alimento != null) {
            this.subtotal = alimento.getPreco() * quantidade;
        } else {
            this.subtotal = 0.0;
        }
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Alimento getAlimento() { return alimento; }
    public void setAlimento(Alimento alimento) { this.alimento = alimento; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        if (alimento != null) {
            this.subtotal = alimento.getPreco() * quantidade;
        } else {
            this.subtotal = 0.0;
        }
    }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
