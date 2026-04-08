package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Contract;
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
                "INSERT INTO contract " +
                        "(id_request, id_pa, start_date, end_date, " +
                        "contract_document, contract_state) " +
                        "VALUES (?, ?, ?, ?, ?, CAST(? AS contract_state_enum))",
                contract.getIdRequest(),
                contract.getIdPa(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getContractDocument(),
                contract.getContractState()
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
                "UPDATE contract SET " +
                        "start_date = ?, end_date = ?, " +
                        "contract_document = ?, " +
                        "contract_state = CAST(? AS contract_state_enum) " +
                        "WHERE id_contract = ?",
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getContractDocument(),
                contract.getContractState(),
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

    // El contrato asociado a una solicitud concreta
    public Contract getContractByRequest(int idRequest) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM contract WHERE id_request = ?",
                    new ContractRowMapper(),
                    idRequest
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Todos los contratos de un PA
    public List<Contract> getContractsByPa(int idPa) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM contract WHERE id_pa = ? " +
                            "ORDER BY start_date DESC",
                    new ContractRowMapper(),
                    idPa
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Contractes actius o finalitzats
    public List<Contract> getContractsByState(String state) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM contract " +
                            "WHERE contract_state = CAST(? AS contract_state_enum) " +
                            "ORDER BY start_date DESC",
                    new ContractRowMapper(),
                    state
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public void updateContractState(int idContract, String state) {
        jdbcTemplate.update(
                "UPDATE contract SET " +
                        "contract_state = CAST(? AS contract_state_enum) " +
                        "WHERE id_contract = ?",
                state,
                idContract
        );
    }

}

