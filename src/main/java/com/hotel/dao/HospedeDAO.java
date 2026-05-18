package com.hotel.dao;

import com.hotel.model.Hospede;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

public class HospedeDAO extends MysqlDAO<Hospede> {

    @Override
    protected Supplier<Hospede> novaInstancia() {
        return Hospede::new;
    }

    public void salvar(Hospede hospede) throws SQLException {
        String sql = "INSERT INTO hospedes (nome, cpf, email, telefone, data_nascimento) VALUES (?, ?, ?, ?, ?)";
        executarUpdate(sql,
                hospede.getNome(),
                hospede.getCpf(),
                hospede.getEmail(),
                hospede.getTelefone(),
                hospede.getDataNascimento() != null ? Date.valueOf(hospede.getDataNascimento()) : null);
    }

    public void atualizar(Hospede hospede) throws SQLException {
        String sql = "UPDATE hospedes SET nome=?, cpf=?, email=?, telefone=?, data_nascimento=? WHERE id=?";
        executarUpdate(sql,
                hospede.getNome(),
                hospede.getCpf(),
                hospede.getEmail(),
                hospede.getTelefone(),
                hospede.getDataNascimento() != null ? Date.valueOf(hospede.getDataNascimento()) : null,
                hospede.getId());
    }

    public void deletar(int id) throws SQLException {
        executarUpdate("DELETE FROM hospedes WHERE id=?", id);
    }

    public Hospede buscarPorId(int id) throws SQLException {
        return buscarUnico("SELECT * FROM hospedes WHERE id=?", id);
    }

    public List<Hospede> listarTodos() throws SQLException {
        return listar("SELECT * FROM hospedes ORDER BY nome");
    }

    public List<Hospede> listar() throws SQLException {
        return listarTodos();
    }

    public List<Hospede> buscarPorNome(String nome) throws SQLException {
        return listar("SELECT * FROM hospedes WHERE nome LIKE ? ORDER BY nome", "%" + nome + "%");
    }
}
