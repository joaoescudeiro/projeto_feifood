/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Escudeiro
 */
public class Usuario {
    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private String endereco;
    private String telefone;

    public Usuario() {
    }

    public Usuario(int id, String nome, String usuario, String senha, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Usuario(String nome, String usuario, String senha, String endereco, String telefone) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}