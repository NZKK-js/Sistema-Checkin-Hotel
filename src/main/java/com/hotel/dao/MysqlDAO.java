package com.hotel.dao;

import com.hotel.model.Mapeavel;
import com.hotel.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private void preencherParametros(PreparedStatement stmt, Object... parametros) throws SQLException {
        for (int i = 0; i < parametros.length; i++) {
            stmt.setObject(i + 1, parametros[i]);
        }
    }
}
