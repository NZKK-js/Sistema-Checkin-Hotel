package com.hotel.dao;

import com.hotel.model.CheckIn;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

public class CheckInDAO extends MysqlDAO<CheckIn> {

    private static final String SELECT_JOIN =
            "SELECT c.*, r.hospede_id, r.quarto_id, r.data_entrada, r.data_saida, " +
            "r.status as reserva_status, " +
            "h.nome as hospede_nome, h.cpf, h.email, h.telefone, " +
            "q.numero, q.tipo, q.preco_diaria " +
            "FROM checkins c " +
            "JOIN reservas r ON c.reserva_id = r.id " +
            "JOIN hospedes h ON r.hospede_id = h.id " +
            "JOIN quartos q ON r.quarto_id = q.id ";

    @Override
    protected Supplier<CheckIn> novaInstancia() {
        return CheckIn::new;
    }

    public void salvar(CheckIn checkIn) throws SQLException {
        String sql = "INSERT INTO checkins (reserva_id, data_checkin, status, observacoes) VALUES (?, NOW(), ?, ?)";
        executarUpdate(sql,
                checkIn.getReservaId(),
                "ATIVO",
                checkIn.getObservacoes());
    }

    public void realizarCheckout(int id) throws SQLException {
        executarUpdate("UPDATE checkins SET data_checkout=NOW(), status='FINALIZADO' WHERE id=?", id);
    }

    public void deletar(int id) throws SQLException {
        executarUpdate("DELETE FROM checkins WHERE id=?", id);
    }

    public CheckIn buscarPorId(int id) throws SQLException {
        return buscarUnico(SELECT_JOIN + "WHERE c.id=?", id);
    }

    public List<CheckIn> listarTodos() throws SQLException {
        return listar(SELECT_JOIN + "ORDER BY c.data_checkin DESC");
    }

    public List<CheckIn> listarAtivos() throws SQLException {
        return listar(SELECT_JOIN + "WHERE c.status='ATIVO' ORDER BY c.data_checkin DESC");
    }
}
