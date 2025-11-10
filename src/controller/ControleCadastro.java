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
    private Usuario usuarioParaEdicao;
    
    public ControleCadastro(CadastroUsuario tela3){
        this.tela3 = tela3;
    }
    
    public ControleCadastro(CadastroUsuario tela3, Usuario usuarioParaEdicao){
        this.tela3 = tela3;
        this.usuarioParaEdicao = usuarioParaEdicao;
    }
    
    public void cadastrarUsuario(){
        String nome = tela3.getInputNome().getText();
        String user = tela3.getInputUsuario().getText();
        String senha = tela3.getInputSenha().getText();
        String endereco = tela3.getInputEndereco().getText();
        String telefone = tela3.getInputTelefone().getText();
        
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConexao();
            UsuarioDAO dao = new UsuarioDAO(conn);
            
            if (usuarioParaEdicao == null) {
                Usuario novoUsuario = new Usuario(nome, user, senha, endereco, telefone);
                dao.inserir(novoUsuario);
                JOptionPane.showMessageDialog(tela3, "Usuário Cadastrado!","Aviso", 
                                            JOptionPane.INFORMATION_MESSAGE);
            } else {
                usuarioParaEdicao.setNome(nome);
                usuarioParaEdicao.setUsuario(user);
                usuarioParaEdicao.setSenha(senha);
                usuarioParaEdicao.setEndereco(endereco);
                usuarioParaEdicao.setTelefone(telefone);
                
                dao.atualizar(usuarioParaEdicao);
                JOptionPane.showMessageDialog(tela3, "Perfil Atualizado!","Aviso", 
                                            JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(tela3, "Erro ao processar o usuário!","Erro", 
                                        JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
