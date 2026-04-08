package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.OviUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class OviUserDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añade un oviUser a la base de datos */
    public void addOviUser(OviUser user) {
        jdbcTemplate.update(
                "INSERT INTO ovi_user (name, surname, dni_nie, birth_date, address, city, " +
                        "postal_code, email, phone, entity, name_tutor, dni_nie_tutor, " +
                        "has_depen_degree, depen_degree, project_life_doc, social_service_center, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.getName(), user.getSurname(), user.getDniNie(), user.getBirthDate(),
                user.getAddress(), user.getCity(), user.getPostalCode(), user.getEmail(),
                user.getPhone(), user.getEntity(), user.getNameTutor(), user.getDniNieTutor(),
                user.getHasDepenDegree(), user.getDepenDegree(), user.getProjectLifeDoc(),
                user.getSocialServiceCenter(), user.getPassword()
        );
    }

    /* Borra un oviUser de la base de datos */
    public void deleteOviUser(int idOviUser) {
        jdbcTemplate.update("DELETE FROM ovi_user WHERE id_ovi_user=?", idOviUser);
    }

    /* Actualiza los atributos del oviUser
    (excepto el id, que es la clave primaria) */
    public void updateOviUser(OviUser user) {
        jdbcTemplate.update(
                "UPDATE ovi_user SET name=?, surname=?, dni_nie=?, birth_date=?, address=?, city=?, " +
                        "postal_code=?, email=?, phone=?, entity=?, name_tutor=?, dni_nie_tutor=?, " +
                        "has_depen_degree=?, depen_degree=?, project_life_doc=?, social_service_center=? " +
                        "WHERE id_ovi_user=?",
                user.getName(), user.getSurname(), user.getDniNie(), user.getBirthDate(),
                user.getAddress(), user.getCity(), user.getPostalCode(), user.getEmail(),
                user.getPhone(), user.getEntity(), user.getNameTutor(), user.getDniNieTutor(),
                user.getHasDepenDegree(), user.getDepenDegree(), user.getProjectLifeDoc(),
                user.getSocialServiceCenter(),
                user.getIdOviUser()
        );
    }

    /* Obtiene el oviUser con el nombre dado. Devuelve null si no existe. */
    public OviUser getOviUser(int idOviUser) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM ovi_user WHERE id_ovi_user=?",
                    new OviUserRowMapper(), idOviUser);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtiene todos los oviUsers. Devuelve una lista vacía si no hay oviUsers */
    public List<OviUser> getOviUsers() {
        try {
            return jdbcTemplate.query("SELECT * FROM ovi_user",
                    new OviUserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<OviUser>();
        }
    }

    // Útil para el técnico: filtra por estado los ovi_user (pending/accepted/refused)
    // Si han sido aceptado para iniciar sesión o no
    public List<OviUser> getOviUsersByState(String state) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM ovi_user WHERE ovi_user_state = CAST(? AS state_enum) " +
                            "ORDER BY surname, name",
                    new OviUserRowMapper(), state
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Útil para el técnico para aceptar o rechazar a un OviUser
    public void updateOviUserState(int idOviUser, String state, String rejectionReason) {
        jdbcTemplate.update(
                "UPDATE ovi_user SET " +
                        "ovi_user_state = CAST(? AS state_enum), rejection_reason = ? " +
                        "WHERE id_ovi_user = ?",
                state, rejectionReason, idOviUser
        );
    }


}