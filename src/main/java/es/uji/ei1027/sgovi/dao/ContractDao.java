package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Contract;
import es.uji.ei1027.sgovi.modelo.OviUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class ContractDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añade un contrato a la base de datos */
    public void addContract(Contract contract) {
        jdbcTemplate.update(
                "INSERT INTO contract (request_id, pa_id, start_date, end_date, contract_document) " +
                        "VALUES (?, ?, ?, ?, ?)",
                contract.getRequestId(), contract.getPaId(),
                contract.getStartDate(), contract.getEndDate(), contract.getContractDocument()
        );
    }

    // DELETE: Elimina un contrato
    public void deleteContract(int idContract) {
        jdbcTemplate.update(
                "DELETE FROM contract WHERE id_contract=?", idContract
        );
    }

    // UPDATE: Modifica un contrato
    public void updateContract(Contract contract) {
        jdbcTemplate.update(
                "UPDATE contract SET request_id=?, pa_id=?, start_date=?, end_date=?, contract_document=? " +
                        "WHERE id_contract=?",
                contract.getRequestId(), contract.getPaId(), contract.getStartDate(),
                contract.getEndDate(), contract.getContractDocument(),
                contract.getIdContract()
        );
    }

    // READ: Obtiene un contrato por su ID. Devuelve null si no existe
    public Contract getContract(int idContract) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM contract WHERE id_contract=?",
                    new ContractRowMapper(),
                    idContract
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // READ ALL: Obtiene la lista de todos los contratos
    public List<Contract> getContracts() {
        try {
            return jdbcTemplate.query("SELECT * FROM contract",
                    new ContractRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Contract>();
        }
    }
}

