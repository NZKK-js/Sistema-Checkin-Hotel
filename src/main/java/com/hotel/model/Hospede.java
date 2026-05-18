package com.hotel.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Hospede implements Mapeavel {

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private LocalDateTime createdAt;

    public Hospede() {}

    public Hospede(int id, String nome, String cpf, String email, String telefone, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    @Override
    public void mapear(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.nome = rs.getString("nome");
        this.cpf = rs.getString("cpf");
        this.email = rs.getString("email");
        this.telefone = rs.getString("telefone");
        Date dataNasc = rs.getDate("data_nascimento");
        if (dataNasc != null) {
            this.dataNascimento = dataNasc.toLocalDate();
        }
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Hospede{id=" + id + ", nome='" + nome + "', cpf='" + cpf + "'}";
    }
}
