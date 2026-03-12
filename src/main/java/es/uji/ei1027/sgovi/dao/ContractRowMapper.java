package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Contract;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ContractRowMapper implements RowMapper<Contract> {

    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {

        Contract contract = new Contract();
        contract.setIdContract(rs.getInt("id_contract"));
        contract.setRequestId(rs.getInt("request_id"));
        contract.setPaId(rs.getInt("pa_id"));
        contract.setStartDate(rs.getObject("start_date", LocalDate.class));
        contract.setEndDate(rs.getObject("end_date", LocalDate.class));
        contract.setContractDocument(rs.getString("contract_document"));
        return contract;
    }

}