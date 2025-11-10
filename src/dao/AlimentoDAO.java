/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Alimento;
import model.Comida;
import model.Bebida;
/**
 *
 * @author Escudeiro
 */
public class AlimentoDAO {

    private Connection conn;

    public AlimentoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public AlimentoDAO() {
        this.conn = Conexao.getConexao();
    }

    public void inserirAlimento(Alimento alimento) {
        String sql = "INSERT INTO tbalimentos (nome, preco, descricao, estabelecimento, tipo, alcoolica, vegetariana) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alimento.getNome());
            stmt.setDouble(2, alimento.getPreco());
            stmt.setString(3, alimento.getDescricao());
            stmt.setString(4, alimento.getEstabelecimento());

            if (alimento instanceof Comida comida) {
                stmt.setString(5, "Comida");
                stmt.setNull(6, Types.BOOLEAN);
                stmt.setBoolean(7, comida.isVegetariana());
            } else if (alimento instanceof Bebida bebida) {
                stmt.setString(5, "Bebida");
                stmt.setBoolean(6, bebida.isAlcoolica());
                stmt.setNull(7, Types.BOOLEAN);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Alimento> buscarPorNome(String nomeBusca) {
        List<Alimento> lista = new ArrayList<>();
        String sql = "SELECT * FROM tbalimentos WHERE LOWER(nome) LIKE LOWER(?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Alimento a = null;

                if ("Comida".equalsIgnoreCase(tipo)) {
                    a = new Comida(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getString("descricao"),
                            rs.getString("estabelecimento"),
                            rs.getBoolean("vegetariana")
                    );
                } else if ("Bebida".equalsIgnoreCase(tipo)) {
                    a = new Bebida(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getString("descricao"),
                            rs.getString("estabelecimento"),
                            rs.getBoolean("alcoolica")
                    );
                }

                if (a != null) {
                    lista.add(a);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Alimento> listarTodos() {
        return buscarPorNome("");
    }

    public Alimento buscarPorId(int id) {
        String sql = "SELECT * FROM tbalimentos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");

                if ("Comida".equalsIgnoreCase(tipo)) {
                    return new Comida(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getString("descricao"),
                            rs.getString("estabelecimento"),
                            rs.getBoolean("vegetariana")
                    );
                } else if ("Bebida".equalsIgnoreCase(tipo)) {
                    return new Bebida(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getString("descricao"),
                            rs.getString("estabelecimento"),
                            rs.getBoolean("alcoolica")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
