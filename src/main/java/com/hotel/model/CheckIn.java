package com.hotel.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CheckIn implements Mapeavel {

    private int id;
    private int reservaId;
    private LocalDateTime dataCheckin;
    private LocalDateTime dataCheckout;
    private String status; // ATIVO, FINALIZADO
    private String observacoes;

    private Reserva reserva;

    public CheckIn() {}

    @Override
    public void mapear(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.reservaId = rs.getInt("reserva_id");
        this.status = rs.getString("status");
        this.observacoes = rs.getString("observacoes");

        Timestamp tsIn = rs.getTimestamp("data_checkin");
        if (tsIn != null) this.dataCheckin = tsIn.toLocalDateTime();
        Timestamp tsOut = rs.getTimestamp("data_checkout");
        if (tsOut != null) this.dataCheckout = tsOut.toLocalDateTime();

        Reserva r = new Reserva();
        r.setId(rs.getInt("reserva_id"));
        r.setHospedeId(rs.getInt("hospede_id"));
        r.setQuartoId(rs.getInt("quarto_id"));
        r.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
        r.setDataSaida(rs.getDate("data_saida").toLocalDate());
        r.setStatus(rs.getString("reserva_status"));

        Hospede h = new Hospede();
        h.setId(rs.getInt("hospede_id"));
        h.setNome(rs.getString("hospede_nome"));
        h.setCpf(rs.getString("cpf"));
        h.setEmail(rs.getString("email"));
        h.setTelefone(rs.getString("telefone"));
        r.setHospede(h);

        Quarto q = new Quarto();
        q.setId(rs.getInt("quarto_id"));
        q.setNumero(rs.getString("numero"));
        q.setTipo(rs.getString("tipo"));
        q.setPrecoDiaria(rs.getBigDecimal("preco_diaria"));
        r.setQuarto(q);

        this.reserva = r;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getReservaId() { return reservaId; }
    public void setReservaId(int reservaId) { this.reservaId = reservaId; }

    public LocalDateTime getDataCheckin() { return dataCheckin; }
    public void setDataCheckin(LocalDateTime dataCheckin) { this.dataCheckin = dataCheckin; }

    public LocalDateTime getDataCheckout() { return dataCheckout; }
    public void setDataCheckout(LocalDateTime dataCheckout) { this.dataCheckout = dataCheckout; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }
}
