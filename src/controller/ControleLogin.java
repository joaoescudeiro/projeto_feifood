/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
import dao.Conexao;
import model.Usuario;
import view.LoginUsuario;
import view.Menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Escudeiro
 */
public class ControleLogin {
    private LoginUsuario tela1;
    
    public ControleLogin(LoginUsuario tela1){
        this.tela1 = tela1;
    }
    
     public void loginUsuario(){
        Usuario usuario = new Usuario(null,tela1.getInputUsuario().getText(),tela1.getInputSenha().getText(),null,null);
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConexao();
            UsuarioDAO dao = new UsuarioDAO(conn);
            ResultSet res = dao.consultar(usuario);
            if(res.next()){
                JOptionPane.showMessageDialog(tela1, "Login efetuado", "Aviso", 
                                                JOptionPane.INFORMATION_MESSAGE);
                String nome = res.getString("nome");
                String user = res.getString("usuario");
                String senha = res.getString("senha");
                String endereco = res.getString("endereco");
                String telefone = res.getString("telefone");
                Menu tela2 = new Menu(new Usuario(nome, user, senha, endereco, telefone));
                tela2.setVisible(true);
                tela1.setVisible(false);
            } else{
                JOptionPane.showMessageDialog(tela1, "Login não efetuado", "Erro", 
                                                JOptionPane.ERROR_MESSAGE);
                
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(tela1, "Erro de conexão", "Erro", 
                                                JOptionPane.ERROR_MESSAGE);
        }
    }
}
