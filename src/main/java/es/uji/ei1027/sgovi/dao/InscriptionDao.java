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
                "INSERT INTO inscription (id_activity, id_ovi_user, id_pa, date) " +
                        "VALUES (?, ?, ?, ?)",
                inscription.getIdActivity(),
                inscription.getIdOviUser(),
                inscription.getIdPa(),
                inscription.getDate()
        );
    }

    public void deleteInscription(int idInscription) {
        jdbcTemplate.update("DELETE FROM inscription WHERE id_inscription = ?", idInscription);
    }

    public void updateInscription(Inscription inscription) {
        jdbcTemplate.update(
                "UPDATE inscription SET id_activity=?, id_ovi_user=?, id_pa=?, date=? WHERE id_inscription=?",
                inscription.getIdActivity(),
                inscription.getIdOviUser(),
                inscription.getIdPa(),
                inscription.getDate(),
                inscription.getIdInscription()
        );
    }

    public Inscription getInscription(int idInscription) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM inscription WHERE id_inscription = ?",
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
                    "SELECT * FROM inscription",
                    new InscriptionRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Inscription>();
        }
    }

    // ── READ (per activitat)
    // Totes les inscripcions d'una activitat
    public List<Inscription> getInscriptionsByActivity(int idActivity) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM inscription WHERE id_activity = ?",
                    new InscriptionRowMapper(),
                    idActivity
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // READ (per OviUser)
    // Totes les activitats a les que s'ha inscrit un OviUser
    public List<Inscription> getInscriptionsByOviUser(int idOviUser) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM inscription WHERE id_ovi_user = ?",
                    new InscriptionRowMapper(),
                    idOviUser
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // READ (per PA)
    // Totes les activitats a les que s'ha inscrit un PA
    public List<Inscription> getInscriptionsByPa(int idPa) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM inscription WHERE id_pa = ?",
                    new InscriptionRowMapper(),
                    idPa
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
    // UPDATE (registrar assistència)
    public void updateHasAttended(int idInscription, boolean hasAttended) {
        jdbcTemplate.update(
                "UPDATE inscription SET has_attended = ? " +
                        "WHERE id_inscription = ?",
                hasAttended,
                idInscription
        );
    }

}