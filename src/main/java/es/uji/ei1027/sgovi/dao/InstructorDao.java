package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class InstructorDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añade un instructor a la base de datos */
    public void addInstructor(Instructor instructor) {
        jdbcTemplate.update(
                "INSERT INTO instructor (name, surname, dni_nie, birth_date, address, email, phone, specialization) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                instructor.getName(), instructor.getSurname(), instructor.getDniNie(), instructor.getBirthDate(),
                instructor.getAddress(), instructor.getEmail(), instructor.getPhone(), instructor.getSpecialization()
        );
    }

    /* Borra un isntructor de la base de datos */
    public void deleteInstructor(int idInstructor) {
        jdbcTemplate.update("DELETE FROM instructor WHERE id_instructor=?", idInstructor);
    }

    /* Actualiza los atributos del instructor
    (excepto el id, que es la clave primaria) */
    public void updateInstructor(Instructor instructor) {
        jdbcTemplate.update(
                "UPDATE instructor SET name=?, surname=?, dni_nie=?, birth_date=?, " +
                        "address=?, email=?, phone=?, specialization=? " +
                        "WHERE id_instructor=?",
                instructor.getName(), instructor.getSurname(), instructor.getDniNie(),
                instructor.getBirthDate(), instructor.getAddress(), instructor.getEmail(),
                instructor.getPhone(), instructor.getSpecialization(),
                instructor.getIdInstructor()
        );
    }

    /* Obtiene el instructor con el nombre dado. Devuelve null si no existe. */
    public Instructor getInstructor(int idInstructor) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM instructor WHERE id_instructor=?",
                    new InstructorRowMapper(), idInstructor);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtiene todos los instructores. Devuelve una lista vacía si no hay instructores */
    public List<Instructor> getInstructors() {
        try {
            return jdbcTemplate.query("SELECT * FROM instructor",
                    new InstructorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Instructor>();
        }
    }
}