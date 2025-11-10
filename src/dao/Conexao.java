/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Escudeiro
 */
public class Conexao {

    private static final String url = "jdbc:postgresql://localhost:5432/feifood";
    private static final String user = "postgres";
    private static final String senha = "123";

    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(url, user, senha);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }
    }
}
