package com.hotel.dao;

import com.hotel.model.Mapeavel;
import com.hotel.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class MysqlDAO<T extends Mapeavel> {

    protected abstract Supplier<T> novaInstancia();

    protected List<T> listar(String sql, Object... parametros) throws SQLException {
        List<T> resultado = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherParametros(stmt, parametros);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T objeto = novaInstancia().get();
                    objeto.mapear(rs);
                    resultado.add(objeto);
                }
            }
        }
        return resultado;
    }

    protected T buscarUnico(String sql, Object... parametros) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherParametros(stmt, parametros);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    T objeto = novaInstancia().get();
                    objeto.mapear(rs);
                    return objeto;
                }
            }
        }
        return null;
    }

    protected void executarUpdate(String sql, Object... parametros) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherParametros(stmt, parametros);
            stmt.executeUpdate();
        }
    }

    /** Executa um INSERT e devolve o ID gerado pelo banco. */
    protected int executarInsert(String sql, Object... parametros) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherParametros(stmt, parametros);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Não foi possível obter o ID gerado pelo banco de dados.");
    }

    /** Executa uma consulta COUNT(*) usando o apelido total. */
    protected int contar(String sql, Object... parametros) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherParametros(stmt, parametros);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("total") : 0;
            }
        }
    }

    private void preencherParametros(PreparedStatement stmt, Object... parametros) throws SQLException {
        for (int i = 0; i < parametros.length; i++) {
            stmt.setObject(i + 1, parametros[i]);
        }
    }
}
