/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Pedido;
import model.PedidoItem;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Escudeiro
 */
public class PedidoDAO {
    private Connection conexao;

    public PedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public int criarPedido(Pedido pedido) {
        String sql = "INSERT INTO tbpedidos (id_usuario, total, avaliacao) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getUsuario().getId());
            stmt.setDouble(2, pedido.getTotal());
            stmt.setInt(3, pedido.getAvaliacao());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Pedido> listarPedidosPorUsuario(Usuario usuario) {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbpedidos WHERE id_usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setUsuario(usuario);
                p.setTotal(rs.getDouble("total"));
                p.setAvaliacao(rs.getInt("avaliacao"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizarAvaliacao(int idPedido, int avaliacao) {
        String sql = "UPDATE tbpedidos SET avaliacao = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, avaliacao);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirPedido(int idPedido) {
        String sql = "DELETE FROM tbpedidos WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
