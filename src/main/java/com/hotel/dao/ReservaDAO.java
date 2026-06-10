package com.hotel.dao;

import com.hotel.model.Reserva;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

public class ReservaDAO extends MysqlDAO<Reserva> {

    private static final String SELECT_JOIN =
            "SELECT r.*, h.nome as hospede_nome, h.cpf, h.email, h.telefone, " +
            "q.numero, q.tipo, q.preco_diaria, q.disponivel, q.descricao " +
            "FROM reservas r " +
            "JOIN hospedes h ON r.hospede_id = h.id " +
            "JOIN quartos q ON r.quarto_id = q.id ";

    @Override
    protected Supplier<Reserva> novaInstancia() {
        return Reserva::new;
    }

    public void salvar(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (hospede_id, quarto_id, data_entrada, data_saida, status) VALUES (?, ?, ?, ?, ?)";
        executarUpdate(sql,
                reserva.getHospedeId(),
                reserva.getQuartoId(),
                Date.valueOf(reserva.getDataEntrada()),
                Date.valueOf(reserva.getDataSaida()),
                reserva.getStatus() != null ? reserva.getStatus() : "PENDENTE");
    }

    /**
     * Atualiza somente os dados editáveis da reserva.
     * O status é preservado e deve ser alterado pelos métodos específicos.
     */
    public void atualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reservas SET hospede_id=?, quarto_id=?, data_entrada=?, data_saida=? WHERE id=?";
        executarUpdate(sql,
                reserva.getHospedeId(),
                reserva.getQuartoId(),
                Date.valueOf(reserva.getDataEntrada()),
                Date.valueOf(reserva.getDataSaida()),
                reserva.getId());
    }

    public void deletar(int id) throws SQLException {
        executarUpdate("DELETE FROM reservas WHERE id=?", id);
    }

    public void atualizarStatus(int id, String status) throws SQLException {
        executarUpdate("UPDATE reservas SET status=? WHERE id=?", status, id);
    }

    public Reserva buscarPorId(int id) throws SQLException {
        return buscarUnico(SELECT_JOIN + "WHERE r.id=?", id);
    }

    public List<Reserva> listarTodas() throws SQLException {
        return listar(SELECT_JOIN + "ORDER BY r.data_entrada DESC");
    }

    public List<Reserva> listar() throws SQLException {
        return listarTodas();
    }

    /** Verifica conflito de período para o mesmo quarto. */
    public boolean existeConflito(int quartoId, Date dataEntrada, Date dataSaida, Integer reservaIgnoradaId)
            throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM reservas "
                + "WHERE quarto_id=? AND status IN ('PENDENTE', 'CONFIRMADA') "
                + "AND data_entrada < ? AND data_saida > ?";

        if (reservaIgnoradaId != null) {
            sql += " AND id <> ?";
            return contar(sql, quartoId, dataSaida, dataEntrada, reservaIgnoradaId) > 0;
        }
        return contar(sql, quartoId, dataSaida, dataEntrada) > 0;
    }

    public List<Reserva> listarConfirmadas() throws SQLException {
        return listar(SELECT_JOIN + "WHERE r.status = 'CONFIRMADA' ORDER BY r.data_entrada");
    }
}
