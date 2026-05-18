package com.hotel.dao;

import com.hotel.model.Quarto;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

public class QuartoDAO extends MysqlDAO<Quarto> {

    @Override
    protected Supplier<Quarto> novaInstancia() {
        return Quarto::new;
    }

    public void salvar(Quarto quarto) throws SQLException {
        String sql = "INSERT INTO quartos (numero, tipo, preco_diaria, disponivel, descricao) VALUES (?, ?, ?, ?, ?)";
        executarUpdate(sql,
                quarto.getNumero(),
                quarto.getTipo(),
                quarto.getPrecoDiaria(),
                quarto.isDisponivel(),
                quarto.getDescricao());
    }

    public void atualizar(Quarto quarto) throws SQLException {
        String sql = "UPDATE quartos SET numero=?, tipo=?, preco_diaria=?, disponivel=?, descricao=? WHERE id=?";
        executarUpdate(sql,
                quarto.getNumero(),
                quarto.getTipo(),
                quarto.getPrecoDiaria(),
                quarto.isDisponivel(),
                quarto.getDescricao(),
                quarto.getId());
    }

    public void deletar(int id) throws SQLException {
        executarUpdate("DELETE FROM quartos WHERE id=?", id);
    }

    public Quarto buscarPorId(int id) throws SQLException {
        return buscarUnico("SELECT * FROM quartos WHERE id=?", id);
    }

    public List<Quarto> listarTodos() throws SQLException {
        return listar("SELECT * FROM quartos ORDER BY numero");
    }

    public List<Quarto> listar() throws SQLException {
        return listarTodos();
    }

    public List<Quarto> listarDisponiveis() throws SQLException {
        return listar("SELECT * FROM quartos WHERE disponivel = TRUE ORDER BY numero");
    }

    public void atualizarDisponibilidade(int id, boolean disponivel) throws SQLException {
        executarUpdate("UPDATE quartos SET disponivel=? WHERE id=?", disponivel, id);
    }
}
