package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.enums.TypePa;
import es.uji.ei1027.sgovi.modelo.PA;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PARowMapper implements RowMapper<PA> {
    public PA mapRow(ResultSet rs, int rowNum) throws SQLException {
        PA pa = new PA();
        pa.setIdPa(rs.getString("id_pa"));
        pa.setDniNie(rs.getString("dni"));
        pa.setName(rs.getString("name"));
        pa.setSurname(rs.getString("surname"));
        pa.setEmail(rs.getString("email"));
        pa.setTypePa(TypePa.valueOf(rs.getString("type_pa")));
        pa.setAddress(rs.getString("address"));
        pa.setEducation(rs.getString("education"));
        pa.setEntity(rs.getString("entity"));
        pa.setHasExperience(rs.getBoolean("has_experience"));
        pa.setExperience(rs.getString("experience"));
        pa.setBirthDate(rs.getDate("birthdate"));
        pa.setCity(rs.getString("city"));
        pa.setComments(rs.getString("hobbies"));
        pa.setHobbies(rs.getString("comments"));
        pa.setCv(rs.getString("cv"));
        return pa;
    }
}
