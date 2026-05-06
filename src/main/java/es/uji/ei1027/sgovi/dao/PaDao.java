package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.PA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class PaDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* CREATE: Añade un nuevo PA a la base de datos */
    public void addPA(PA pa) {
        jdbcTemplate.update(
                "INSERT INTO pa (name, surname, dni_nie, birth_date, address, city, " +
                        //"postal_code, email, phone, type_pa, type_service, education, " +
                        "postal_code, email, phone, type_pa, education, " +
                        "experience, hobbies, gender, comments, cv, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        //"CAST(? AS type_pa_enum), CAST(? AS type_accompaniment_enum), " +
                        "CAST(? AS type_pa_enum), " +
                        "?, ?, ?, ?, ?, ?, ?)",
                pa.getName(),
                pa.getSurname(),
                pa.getDniNie(),
                pa.getBirthDate(),
                pa.getAddress(),
                pa.getCity().name(),
                pa.getPostalCode(),
                pa.getEmail(),
                pa.getPhone(),
                pa.getTypePa().name(),
                pa.getEducation().name(),
                pa.getExperience(),
                pa.getHobbies().name(),
                pa.getGender().name(),
                pa.getComments(),
                pa.getCv(),
                pa.getPassword()
        );
    }

    /* DELETE: Elimina un PA */
    public void deletePA(int idPA) {
        jdbcTemplate.update(
                "DELETE FROM pa WHERE id_pa=?",
                idPA
        );
    }

    /* UPDATE: Modifica un PA completo */
    public void updatePA(PA pa) {
        jdbcTemplate.update(
                "UPDATE pa SET name=?, surname=?, dni_nie=?, birth_date=?, " +
                        "address=?, city=?, postal_code=?, email=?, phone=?, " +
                        "type_pa=CAST(? AS type_pa_enum), " +
                        //"type_service=CAST(? AS type_accompaniment_enum), " +
                        "education=?, experience=?, hobbies=?, " +
                        "gender=?, comments=?, cv=? " +
                        "WHERE id_pa=?",
                pa.getName(), pa.getSurname(), pa.getDniNie(), pa.getBirthDate(),
                pa.getAddress(), pa.getCity(), pa.getPostalCode(), pa.getEmail(),
                //pa.getPhone(), pa.getTypePa(), pa.getTypeService(), pa.getEducation(),
                pa.getPhone(), pa.getTypePa(), pa.getEducation(),
                pa.getExperience(), pa.getHobbies(), pa.getGender(),
                pa.getComments(), pa.getCv(), pa.getIdPa()
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
                    "SELECT * FROM pa;",
                    new PARowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<PA>();
        }
    }

    // El técnico filtra PA por estado (pending/accepted/refused)
    public List<PA> getPasByState(String state) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM pa WHERE pa_state = CAST(? AS state_enum) " +
                            "ORDER BY surname, name",
                    new PARowMapper(), state
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // El técnico acepta o rechaza un PA
    public void updatePaState(int idPa, String state, String rejectionReason) {
        jdbcTemplate.update(
                "UPDATE pa SET " +
                        "pa_state = CAST(? AS state_enum), rejection_reason = ? " +
                        "WHERE id_pa = ?",
                state, rejectionReason, idPa
        );
    }

    // Devuelve un PA por su dniNie
    public PA getPaByDni(String dniNie) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM pa WHERE dni_nie = ?",
                    new PARowMapper(), dniNie
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
