/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Escudeiro
 */
public class Comida extends Alimento {
    private boolean vegetariana;

    public Comida(int id, String nome, double preco, String descricao, String estabelecimento, boolean vegetariana) {
        super(id, nome, preco, descricao, estabelecimento);
        this.vegetariana = vegetariana;
    }

    public Comida(String nome, double preco, String descricao, String estabelecimento, boolean vegetariana) {
        super(nome, preco, descricao, estabelecimento);
        this.vegetariana = vegetariana;
    }

    public boolean isVegetariana() {
        return vegetariana;
    }

    public void setVegetariana(boolean vegetariana) {
        this.vegetariana = vegetariana;
    }

    @Override
    public String toString() {
        return super.toString() + (vegetariana ? " (Vegetariana)" : "");
    }
}
