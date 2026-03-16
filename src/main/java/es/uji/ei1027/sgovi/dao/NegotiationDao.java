package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Negotiation;
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
                "INSERT INTO negotiation (id_negotiation, request_id, pa_id, start_date) VALUES (?, ?, ?, ?)",
                negotiation.getIdNegotiation(),
                negotiation.getRequestId(),
                negotiation.getPaId(),
                negotiation.getStartDate()
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
                "UPDATE negotiation SET request_id=?, pa_id=?, start_date=? WHERE id_negotiation=?",
                negotiation.getRequestId(),
                negotiation.getPaId(),
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

    /* Obtiene todas las negociaciones. Devuelve una lista vacía si no hay negociaciones */
    public List<Negotiation> getNegotiations() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM negotiation",
                    new NegotiationRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Negotiation>();
        }
    }
}