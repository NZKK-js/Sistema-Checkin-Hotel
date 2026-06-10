package com.hotel.service;

import com.hotel.dao.QuartoDAO;
import com.hotel.dao.ReservaDAO;
import com.hotel.model.Reserva;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ReservaService {

    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final QuartoDAO quartoDAO = new QuartoDAO();

    public void criar(Reserva reserva) throws Exception {
        validar(reserva);
        validarConflito(reserva, null);
        reserva.setStatus("PENDENTE");
        reservaDAO.salvar(reserva);
    }

    public void confirmar(int id) throws Exception {
        Reserva reserva = reservaDAO.buscarPorId(id);
        if (reserva == null) {
            throw new Exception("Reserva não encontrada.");
        }
        if (!"PENDENTE".equals(reserva.getStatus())) {
            throw new Exception("Apenas reservas pendentes podem ser confirmadas.");
        }
        validarConflito(reserva, reserva.getId());
        reservaDAO.atualizarStatus(id, "CONFIRMADA");
    }

    public void cancelar(int id) throws Exception {
        Reserva reserva = reservaDAO.buscarPorId(id);
        if (reserva == null) {
            throw new Exception("Reserva não encontrada.");
        }
        if ("CANCELADA".equals(reserva.getStatus()) || "FINALIZADA".equals(reserva.getStatus())) {
            throw new Exception("Esta reserva já foi " + reserva.getStatus().toLowerCase() + ".");
        }
        reservaDAO.atualizarStatus(id, "CANCELADA");
        quartoDAO.atualizarDisponibilidade(reserva.getQuartoId(), true);
    }

    public void atualizar(Reserva reserva) throws Exception {
        validar(reserva);
        Reserva existente = reservaDAO.buscarPorId(reserva.getId());
        if (existente == null) {
            throw new Exception("Reserva não encontrada.");
        }
        if ("FINALIZADA".equals(existente.getStatus()) || "CANCELADA".equals(existente.getStatus())) {
            throw new Exception("Não é possível editar uma reserva finalizada ou cancelada.");
        }
        validarConflito(reserva, reserva.getId());
        reservaDAO.atualizar(reserva);
    }

    public void excluir(int id) throws Exception {
        Reserva reserva = reservaDAO.buscarPorId(id);
        if (reserva == null) {
            throw new Exception("Reserva não encontrada.");
        }
        reservaDAO.deletar(id);
    }

    public Reserva buscarPorId(int id) throws SQLException {
        return reservaDAO.buscarPorId(id);
    }

    public List<Reserva> listarTodas() throws SQLException {
        return reservaDAO.listarTodas();
    }

    public List<Reserva> listarConfirmadas() throws SQLException {
        return reservaDAO.listarConfirmadas();
    }

    private void validar(Reserva reserva) throws Exception {
        if (reserva.getHospedeId() <= 0) throw new Exception("Selecione um hóspede válido.");
        if (reserva.getQuartoId() <= 0) throw new Exception("Selecione um quarto válido.");
        if (reserva.getDataEntrada() == null) throw new Exception("A data de entrada é obrigatória.");
        if (reserva.getDataSaida() == null) throw new Exception("A data de saída é obrigatória.");
        if (!reserva.getDataSaida().isAfter(reserva.getDataEntrada())) {
            throw new Exception("A data de saída deve ser posterior à data de entrada.");
        }
        if (reserva.getDataEntrada().isBefore(LocalDate.now())) {
            throw new Exception("A data de entrada não pode ser no passado.");
        }
    }

    private void validarConflito(Reserva reserva, Integer reservaIgnoradaId) throws Exception {
        boolean existeConflito = reservaDAO.existeConflito(
                reserva.getQuartoId(),
                Date.valueOf(reserva.getDataEntrada()),
                Date.valueOf(reserva.getDataSaida()),
                reservaIgnoradaId
        );
        if (existeConflito) {
            throw new Exception("Este quarto já possui uma reserva no período informado.");
        }
    }
}
