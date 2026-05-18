package com.hotel.service;

import com.hotel.dao.QuartoDAO;
import com.hotel.model.Quarto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class QuartoService {

    private final QuartoDAO quartoDAO = new QuartoDAO();

    public void cadastrar(Quarto quarto) throws Exception {
        validar(quarto);
        quartoDAO.salvar(quarto);
    }

    public void atualizar(Quarto quarto) throws Exception {
        validar(quarto);
        quartoDAO.atualizar(quarto);
    }

    public void excluir(int id) throws Exception {
        quartoDAO.deletar(id);
    }

    public Quarto buscarPorId(int id) throws SQLException {
        return quartoDAO.buscarPorId(id);
    }

    public List<Quarto> listarTodos() throws SQLException {
        return quartoDAO.listarTodos();
    }

    public List<Quarto> listarDisponiveis() throws SQLException {
        return quartoDAO.listarDisponiveis();
    }

    private void validar(Quarto quarto) throws Exception {
        if (quarto.getNumero() == null || quarto.getNumero().trim().isEmpty()) {
            throw new Exception("O número do quarto é obrigatório.");
        }
        if (quarto.getTipo() == null || quarto.getTipo().trim().isEmpty()) {
            throw new Exception("O tipo do quarto é obrigatório.");
        }
        if (quarto.getPrecoDiaria() == null
                || quarto.getPrecoDiaria().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O preço da diária deve ser maior que zero.");
        }
    }
}
