/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Escudeiro
 */
public class Bebida extends Alimento implements Imposto_Alcool {
    
    private boolean alcoolica;

    public Bebida(int id, String nome, double preco, String descricao, String estabelecimento, boolean alcoolica) {
        super(id, nome, preco, descricao, estabelecimento);
        this.alcoolica = alcoolica;
    }

    public Bebida(String nome, double preco, String descricao, String estabelecimento, boolean alcoolica) {
        super(nome, preco, descricao, estabelecimento);
        this.alcoolica = alcoolica;
    }

    public boolean isAlcoolica() {
        return alcoolica;
    }

    public void setAlcoolica(boolean alcoolica) {
        this.alcoolica = alcoolica;
    }

    @Override
    public double calcularImpostoAlcool() {
        if (alcoolica) {
            return getPreco() * 0.25;
        }
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + (alcoolica ? " (Alcoólica)" : " (Sem Álcool)");
    }
}
