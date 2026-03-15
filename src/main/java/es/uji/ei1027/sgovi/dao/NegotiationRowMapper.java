package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.model.Negotiation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NegotiationRowMapper implements RowMapper<Negotiation> {

    @Override
    public Negotiation mapRow(ResultSet rs, int rowNum) throws SQLException {

        Negotiation negotiation = new Negotiation();

        negotiation.setIdNegotiation(rs.getInt("id_negotiation"));
        negotiation.setRequestId(rs.getInt("request_id"));
        negotiation.setPaId(rs.getInt("pa_id"));
        negotiation.setStartDate(rs.getDate("start_date").toLocalDate());

        return negotiation;
    }
}