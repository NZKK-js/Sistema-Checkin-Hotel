package com.hotel.service;

import com.hotel.dao.CheckInDAO;
import com.hotel.dao.QuartoDAO;
import com.hotel.dao.ReservaDAO;
import com.hotel.model.CheckIn;
import com.hotel.model.Reserva;

import java.sql.SQLException;
import java.util.List;

public class CheckInService {

    private final CheckInDAO checkInDAO = new CheckInDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final QuartoDAO quartoDAO = new QuartoDAO();

    public void realizarCheckIn(int reservaId, String observacoes) throws Exception {
        Reserva reserva = reservaDAO.buscarPorId(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva não encontrada.");
        }
        if (!"CONFIRMADA".equals(reserva.getStatus())) {
            throw new Exception("Somente reservas confirmadas podem realizar check-in.");
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setReservaId(reservaId);
        checkIn.setObservacoes(observacoes);
        checkInDAO.salvar(checkIn);

        quartoDAO.atualizarDisponibilidade(reserva.getQuartoId(), false);
    }

    public void realizarCheckout(int checkInId) throws Exception {
        CheckIn checkIn = checkInDAO.buscarPorId(checkInId);
        if (checkIn == null) {
            throw new Exception("Check-in não encontrado.");
        }
        if (!"ATIVO".equals(checkIn.getStatus())) {
            throw new Exception("Este check-in já foi finalizado.");
        }

        checkInDAO.realizarCheckout(checkInId);

        int quartoId = checkIn.getReserva().getQuarto().getId();
        quartoDAO.atualizarDisponibilidade(quartoId, true);
        reservaDAO.atualizarStatus(checkIn.getReservaId(), "FINALIZADA");
    }

    public void cancelar(int id) throws Exception {
        checkInDAO.deletar(id);
    }

    public CheckIn buscarPorId(int id) throws SQLException {
        return checkInDAO.buscarPorId(id);
    }

    public List<CheckIn> listarTodos() throws SQLException {
        return checkInDAO.listarTodos();
    }

    public List<CheckIn> listarAtivos() throws SQLException {
        return checkInDAO.listarAtivos();
    }
}
