package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.PACandidate;
import es.uji.ei1027.sgovi.modelo.PA;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PACandidateRowMapper implements RowMapper<PACandidate> {

    @Override
    public PACandidate mapRow(ResultSet rs, int rowNum) throws SQLException {

        PA pa = new PARowMapper().mapRow(rs, rowNum);

        PACandidate dto = new PACandidate();

        dto.setPa(pa);
        dto.setNegotiationState(rs.getString("negotiation_state"));
        dto.setContractState(rs.getString("contract_state"));

        return dto;
    }
}
