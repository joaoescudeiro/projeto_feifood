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
import model.PedidoAtual;

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

    public ControleLogin(LoginUsuario tela1) {
        this.tela1 = tela1;
    }

    public void loginUsuario() {
        Usuario usuario = new Usuario(
            null,
            tela1.getInputUsuario().getText(),
            tela1.getInputSenha().getText(),
            null,
            null
        );

        try (Connection conn = Conexao.getConexao()) {
            UsuarioDAO dao = new UsuarioDAO(conn);
            ResultSet res = dao.consultar(usuario);

            if (res.next()) {
                JOptionPane.showMessageDialog(tela1, "Login efetuado com sucesso!", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);

                int id = res.getInt("id");
                String nome = res.getString("nome");
                String user = res.getString("usuario");
                String senha = res.getString("senha");
                String endereco = res.getString("endereco");
                String telefone = res.getString("telefone");

                Usuario usuarioLogado = new Usuario(id, nome, user, senha, endereco, telefone);
                
                PedidoAtual.iniciarNovoPedido(usuarioLogado);
                
                Menu tela2 = new Menu(usuarioLogado);
                tela2.setVisible(true);
                tela1.dispose();

            } else {
                JOptionPane.showMessageDialog(tela1, "Usuário ou senha incorretos.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(tela1, "Erro ao conectar ao banco de dados.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}