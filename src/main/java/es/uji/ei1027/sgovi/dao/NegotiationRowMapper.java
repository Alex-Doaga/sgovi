package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Negotiation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class NegotiationRowMapper implements RowMapper<Negotiation> {

    @Override
    public Negotiation mapRow(ResultSet rs, int rowNum) throws SQLException {

        Negotiation negotiation = new Negotiation();

        negotiation.setIdNegotiation(rs.getInt("id_negotiation"));
        negotiation.setIdRequest(rs.getInt("id_request"));
        negotiation.setIdPa(rs.getInt("id_pa"));
        negotiation.setStartDate(rs.getObject("start_date", LocalDate.class));
        negotiation.setNegotiationState(rs.getString("negotiation_state"));

        return negotiation;
    }
}