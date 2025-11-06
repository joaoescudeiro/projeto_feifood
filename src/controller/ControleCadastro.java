/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
import dao.Conexao;
import model.Usuario;
import view.CadastroUsuario;

import dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author Escudeiro
 */
public class ControleCadastro {
     private CadastroUsuario tela3;
    
    public ControleCadastro(CadastroUsuario tela3){
        this.tela3 = tela3;
    }
    
    public void cadastrarUsuario(){
        String nome = tela3.getInputNome().getText();
        String user = tela3.getInputUsuario().getText();
        String senha = tela3.getInputSenha().getText();
        String endereco = tela3.getInputEndereco().getText();
        String telefone = tela3.getInputTelefone().getText();
        Usuario usuario = new Usuario(nome, user, senha, endereco, telefone);
        
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            UsuarioDAO dao = new UsuarioDAO(conn);
            dao.inserir(usuario);
            JOptionPane.showMessageDialog(tela3, "Usuario Cadastrado!","Aviso", 
                                        JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(tela3, "Usuário não cadastrado!","Erro", 
                                        JOptionPane.ERROR_MESSAGE);
        }
    }
}
