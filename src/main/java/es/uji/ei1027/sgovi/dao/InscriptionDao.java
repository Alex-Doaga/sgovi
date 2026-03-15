package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InscriptionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addInscription(Inscription inscription) {
        jdbcTemplate.update(
                "INSERT INTO Inscription (id_inscription, activity_id, ovi_user_id, pa_id, date) VALUES(?, ?, ?, ?, ?)",
                inscription.getIdInscription(),
                inscription.getActivityId(),
                inscription.getOviUserId(),
                inscription.getPaId(),
                inscription.getDate()
        );
    }

    public void deleteInscription(int idInscription) {
        jdbcTemplate.update("DELETE FROM Inscription WHERE id_inscription = ?", idInscription);
    }

    public void updateInscription(Inscription inscription) {
        jdbcTemplate.update(
                "UPDATE Inscription SET activity_id = ?, ovi_user_id = ?, pa_id = ?, date = ? WHERE id_inscription = ?",
                inscription.getActivityId(),
                inscription.getOviUserId(),
                inscription.getPaId(),
                inscription.getDate(),
                inscription.getIdInscription()
        );
    }

    public Inscription getInscription(int idInscription) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Inscription WHERE id_inscription = ?",
                    new InscriptionRowMapper(),
                    idInscription
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Inscription> getInscriptions() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Inscription",
                    new InscriptionRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Inscription>();
        }
    }
}