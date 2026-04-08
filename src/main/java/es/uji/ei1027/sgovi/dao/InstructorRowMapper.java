package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Instructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class InstructorRowMapper implements RowMapper<Instructor> {

    public Instructor mapRow(ResultSet rs, int rowNum) throws SQLException {

        Instructor instructor = new Instructor();
        instructor.setIdInstructor(rs.getInt("id_instructor"));
        instructor.setName(rs.getString("name"));
        instructor.setSurname(rs.getString("surname"));
        instructor.setDniNie(rs.getString("dni_nie"));
        instructor.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        instructor.setAddress(rs.getString("address"));
        instructor.setEmail(rs.getString("email"));
        instructor.setPhone(rs.getString("phone"));
        instructor.setSpecialization(rs.getString("specialization"));
        return instructor;
    }

}