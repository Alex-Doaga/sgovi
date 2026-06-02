package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PANegotiationRowMapper implements RowMapper<PANegotiation> {

    @Override
    public PANegotiation mapRow(ResultSet rs, int rowNum) throws SQLException {

        OviUser oviUser = new OviUserRowMapper().mapRow(rs, rowNum);
        Request request = new RequestRowMapper().mapRow(rs, rowNum);
        Negotiation negotiation = new NegotiationRowMapper().mapRow(rs, rowNum);

        PANegotiation dto = new PANegotiation();

        dto.setOviUser(oviUser);
        dto.setRequest(request);
        dto.setNegotiation(negotiation);

        return dto;
    }
}
