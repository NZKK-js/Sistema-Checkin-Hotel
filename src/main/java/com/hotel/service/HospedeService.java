package com.hotel.service;

import com.hotel.dao.HospedeDAO;
import com.hotel.model.Hospede;

import java.sql.SQLException;
import java.util.List;

public class HospedeService {

    private final HospedeDAO hospedeDAO = new HospedeDAO();

    public void cadastrar(Hospede hospede) throws Exception {
        validar(hospede);
        hospedeDAO.salvar(hospede);
    }

    public void atualizar(Hospede hospede) throws Exception {
        validar(hospede);
        hospedeDAO.atualizar(hospede);
    }

    public void excluir(int id) throws Exception {
        hospedeDAO.deletar(id);
    }

    public Hospede buscarPorId(int id) throws SQLException {
        return hospedeDAO.buscarPorId(id);
    }

    public List<Hospede> listarTodos() throws SQLException {
        return hospedeDAO.listarTodos();
    }

    public List<Hospede> buscarPorNome(String nome) throws SQLException {
        return hospedeDAO.buscarPorNome(nome);
    }

    private void validar(Hospede hospede) throws Exception {
        if (hospede.getNome() == null || hospede.getNome().trim().isEmpty()) {
            throw new Exception("O nome do hóspede é obrigatório.");
        }
        if (hospede.getCpf() == null || hospede.getCpf().trim().isEmpty()) {
            throw new Exception("O CPF do hóspede é obrigatório.");
        }
        if (hospede.getEmail() == null || hospede.getEmail().trim().isEmpty()) {
            throw new Exception("O e-mail do hóspede é obrigatório.");
        }
        if (!hospede.getEmail().contains("@")) {
            throw new Exception("O e-mail informado é inválido.");
        }

        if (!cpfValido(hospede.getCpf())) {
            throw new Exception("O CPF informado é inválido. Verifique os números digitados.");
        }
    }

    private boolean cpfValido(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }

        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) primeiroDigito = 0;

        if (primeiroDigito != (cpf.charAt(9) - '0')) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }

        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) segundoDigito = 0;

        return segundoDigito == (cpf.charAt(10) - '0');
    }
}
