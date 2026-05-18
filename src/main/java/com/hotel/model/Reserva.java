package com.hotel.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reserva implements Mapeavel {

    private int id;
    private int hospedeId;
    private int quartoId;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String status; // PENDENTE, CONFIRMADA, CANCELADA, FINALIZADA
    private LocalDateTime createdAt;

    private Hospede hospede;
    private Quarto quarto;

    public Reserva() {}

    @Override
    public void mapear(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.hospedeId = rs.getInt("hospede_id");
        this.quartoId = rs.getInt("quarto_id");
        this.dataEntrada = rs.getDate("data_entrada").toLocalDate();
        this.dataSaida = rs.getDate("data_saida").toLocalDate();
        this.status = rs.getString("status");

        Hospede h = new Hospede();
        h.setId(rs.getInt("hospede_id"));
        h.setNome(rs.getString("hospede_nome"));
        h.setCpf(rs.getString("cpf"));
        h.setEmail(rs.getString("email"));
        h.setTelefone(rs.getString("telefone"));
        this.hospede = h;

        Quarto q = new Quarto();
        q.setId(rs.getInt("quarto_id"));
        q.setNumero(rs.getString("numero"));
        q.setTipo(rs.getString("tipo"));
        q.setPrecoDiaria(rs.getBigDecimal("preco_diaria"));
        q.setDisponivel(rs.getBoolean("disponivel"));
        q.setDescricao(rs.getString("descricao"));
        this.quarto = q;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHospedeId() { return hospedeId; }
    public void setHospedeId(int hospedeId) { this.hospedeId = hospedeId; }

    public int getQuartoId() { return quartoId; }
    public void setQuartoId(int quartoId) { this.quartoId = quartoId; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Hospede getHospede() { return hospede; }
    public void setHospede(Hospede hospede) { this.hospede = hospede; }

    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }

    public long getDiarias() {
        if (dataEntrada != null && dataSaida != null) {
            return ChronoUnit.DAYS.between(dataEntrada, dataSaida);
        }
        return 0;
    }
}
