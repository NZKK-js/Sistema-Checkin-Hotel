package com.hotel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hotel_checkin?useSSL=false&serverTimezone=America/Sao_Paulo&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static ConnectionFactory instancia;

    private ConnectionFactory() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado!", e);
        }
    }

    public static synchronized ConnectionFactory getInstance() {
        if (instancia == null) {
            instancia = new ConnectionFactory();
        }
        return instancia;
    }

    public Connection abrirConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().abrirConexao();
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
