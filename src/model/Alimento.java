package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Escudeiro
 */
public abstract class Alimento {
    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private String estabelecimento;
    
    public Alimento(int id, String nome, double preco, String descricao, String estabelecimento) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.estabelecimento = estabelecimento;
    }
    
    public Alimento(String nome, double preco, String descricao, String estabelecimento) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.estabelecimento = estabelecimento;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getEstabelecimento() { return estabelecimento; }
    public void setEstabelecimento(String estabelecimento) { this.estabelecimento = estabelecimento; }

    @Override
    public String toString() {
        return nome + " - R$ " + preco + " (" + estabelecimento + ")";
    }
}
