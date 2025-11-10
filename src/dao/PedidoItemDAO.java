/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.PedidoItem;
import model.Alimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Escudeiro
 */
public class PedidoItemDAO {
    private Connection conexao;

    public PedidoItemDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void adicionarItem(int idPedido, PedidoItem item) {
        String sql = "INSERT INTO tbpedido_itens (id_pedido, id_alimento, quantidade, subtotal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.setInt(2, item.getAlimento().getId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getSubtotal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PedidoItem> listarItensPorPedido(int idPedido) {
        List<PedidoItem> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbpedido_itens WHERE id_pedido = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PedidoItem item = new PedidoItem();
                item.setId(rs.getInt("id"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setSubtotal(rs.getDouble("subtotal"));

                AlimentoDAO alimentoDAO = new AlimentoDAO(conexao);
                Alimento a = alimentoDAO.buscarPorId(rs.getInt("id_alimento"));
                
                if (a == null) {
                    System.err.println("Aviso: Alimento com ID " + rs.getInt("id_alimento") + " não encontrado para o item de pedido ID " + rs.getInt("id"));
                    continue;
                }
                
                item.setAlimento(a);

                lista.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
