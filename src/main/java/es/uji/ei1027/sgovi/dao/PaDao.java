package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.model.PA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class PaDao {
    private JdbcTemplate jdbcTemplate;

    /* CREATE: Añade un nuevo PA a la base de datos */
    public void addPA(PA pa) {
        jdbcTemplate.update(
                "INSERT INTO pa (id_pa, name, surname, dni_nie, birthdate, address, city, postal_code, email, type_pa, education, entity, experience, hobbies, comments, cv) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                pa.getIdPa(), pa.getName(), pa.getSurname(), pa.getDniNie(),
                pa.getBirthDate(), pa.getAddress(), pa.getCity(), pa.getPostalCode(),
                pa.getEmail(), pa.getTypePa().name(), pa.getEducation(),
                pa.getEntity(), pa.getExperience(), pa.getHobbies(),
                pa.getComments(), pa.getCv()
        );
    }

    /* DELETE: Elimina un PA */
    public void deletePA(int idPA) {
        jdbcTemplate.update(
                "DELETE FROM pa WHERE id_pa=?", idPA
        );
    }

    /* UPDATE: Modifica un PA completo */
    public void updatePA(PA pa) {
        jdbcTemplate.update(
                "UPDATE pa SET name=?, surname=?, dni_nie=?, birthdate=?, address=?, city=?, postal_code=?, email=?, type_pa=?, education=?, entity=?, experience=?, hobbies=?, comments=?, cv=? " +
                        "WHERE id_pa=?",
                pa.getName(), pa.getSurname(), pa.getDniNie(),
                pa.getBirthDate(), pa.getAddress(), pa.getCity(), pa.getPostalCode(),
                pa.getEmail(), pa.getTypePa().name(), pa.getEducation(),
                pa.getEntity(), pa.getExperience(), pa.getHobbies(),
                pa.getComments(), pa.getCv(),
                pa.getIdPa()
        );
    }

    /* READ: Obtiene un PA por su ID. Devuelve null si no existe */
    public PA getPA(int idPA) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM pa WHERE id_pa=?",
                    new PARowMapper(),
                    idPA
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtiene todos los PA. Devuelve una lista vacía si no hay PA */
    public List<PA> getPAs() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM pa",
                    new PARowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<PA>();
        }
    }
}
