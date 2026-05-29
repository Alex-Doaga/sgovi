package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.dto.PACandidateDTO;
import es.uji.ei1027.sgovi.modelo.PA;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PACandidateDTORowMapper implements RowMapper<PACandidateDTO> {

    @Override
    public PACandidateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        PA pa = new PARowMapper().mapRow(rs, rowNum);

        PACandidateDTO dto = new PACandidateDTO();

        dto.setPa(pa);
        dto.setNegotiationState(rs.getString("negotiation_state"));
        dto.setContractState(rs.getString("contract_state"));

        return dto;
    }
}
