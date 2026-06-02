package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Negotiation;
import es.uji.ei1027.sgovi.modelo.PACandidateNegotiation;
import es.uji.ei1027.sgovi.modelo.PANegotiation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class NegotiationDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* CREATE: Añade una nueva negociación a la base de datos */
    public void addNegotiation(Negotiation negotiation) {
        jdbcTemplate.update(
                "INSERT INTO negotiation (id_request, id_pa, start_date, negotiation_state) " +
                        "VALUES (?, ?, ?, CAST(? AS negotiation_state_enum))",
                negotiation.getIdRequest(),
                negotiation.getIdPa(),
                negotiation.getStartDate(),
                negotiation.getNegotiationState()
        );
    }

    /* DELETE: Elimina una negociación */
    public void deleteNegotiation(int idNegotiation) {
        jdbcTemplate.update(
                "DELETE FROM negotiation WHERE id_negotiation=?",
                idNegotiation
        );
    }

    /* UPDATE: Modifica una negociación completa */
    public void updateNegotiation(Negotiation negotiation) {
        jdbcTemplate.update(
                "UPDATE negotiation SET id_request=?, id_pa=?, start_date=? WHERE id_negotiation=?",
                negotiation.getIdRequest(),
                negotiation.getIdPa(),
                negotiation.getStartDate(),
                negotiation.getIdNegotiation()
        );
    }

    /* READ: Obtiene una negociación por su ID. Devuelve null si no existe */
    public Negotiation getNegotiation(int idNegotiation) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM negotiation WHERE id_negotiation=?",
                    new NegotiationRowMapper(),
                    idNegotiation
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtiene todas las negociaciones de un usuario Ovi. Devuelve una lista vacía si no hay negociaciones */
    public List<PACandidateNegotiation> getNegotiationsByOviUser(int idOviUser) {
        try {
            return jdbcTemplate.query(
                    "SELECT pa.*, n.negotiation_state, null as contract_state, r.id_request " +
                            "FROM negotiation AS n " +
                            "INNER JOIN request AS r ON r.id_request = n.id_request " +
                            "INNER JOIN pa ON pa.id_pa = n.id_pa " +
                            "WHERE r.ovi_user_id = ? " +
                            "ORDER BY n.start_date DESC",
                    new PACandidateNegotiationRowMapper(),
                    idOviUser
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    /* Obtiene todas las negociaciones de un PA. Devuelve una lista vacía si no hay negociaciones */
    public List<PANegotiation> getNegotiationsByPA(int idPa) {
        try {
            return jdbcTemplate.query(
                    "SELECT n.*, r.*, u.* " +
                            "FROM negotiation n " +
                            "INNER JOIN request r ON r.id_request = n.id_request " +
                            "INNER JOIN ovi_user u ON u.id_ovi_user = r.ovi_user_id " +
                            "WHERE n.id_pa = ? " +
                            "ORDER BY n.start_date DESC",
                    new PANegotiationRowMapper(),
                    idPa
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Todas las negociaciones de una solicitud concreta
    public List<Negotiation> getNegotiationsByRequest(int idRequest) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM negotiation WHERE id_request = ? " +
                            "ORDER BY start_date DESC;",
                    new NegotiationRowMapper(),
                    idRequest
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Todas las negociaciones de un PA
    public List<Negotiation> getNegotiationsByPa(int idPa) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM negotiation WHERE id_pa = ? " +
                            "ORDER BY start_date DESC",
                    new NegotiationRowMapper(),
                    idPa
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Negociación de un asistente personal en una solicitud concreta
    // Devuelve una única negociación o null si no existe (restricción UNIQUE en BD)
    public Negotiation getNegotiationByPaInRequest(int idPa, int idRequest) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM negotiation WHERE id_pa = ? AND id_request = ? " +
                            "ORDER BY start_date DESC",
                    new NegotiationRowMapper(),
                    idPa,
                    idRequest
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Cambio de estado de la negociación (hablando/sinHablar)
    public void updateNegotiationState(int idNegotiation, String state) {
        jdbcTemplate.update(
                "UPDATE negotiation SET " +
                        "negotiation_state = CAST(? AS negotiation_state_enum) " +
                        "WHERE id_negotiation = ?",
                state,
                idNegotiation
        );
    }


}