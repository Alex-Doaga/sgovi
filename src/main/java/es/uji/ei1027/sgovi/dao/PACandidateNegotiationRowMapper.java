package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.PA;
import es.uji.ei1027.sgovi.modelo.PACandidateNegotiation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PACandidateNegotiationRowMapper implements RowMapper<PACandidateNegotiation> {

    @Override
    public PACandidateNegotiation mapRow(ResultSet rs, int rowNum) throws SQLException {

        PA pa = new PARowMapper().mapRow(rs, rowNum);

        PACandidateNegotiation dto = new PACandidateNegotiation();

        dto.setPa(pa);
        dto.setNegotiationState(rs.getString("negotiation_state"));
        dto.setContractState(rs.getString("contract_state"));
        dto.setRequestId(rs.getInt("id_request"));

        return dto;
    }
}
