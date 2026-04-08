package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.PA;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PARowMapper implements RowMapper<PA> {
    public PA mapRow(ResultSet rs, int rowNum) throws SQLException {
        PA pa = new PA();
        pa.setIdPa(rs.getInt("id_pa"));
        pa.setDniNie(rs.getString("dni_nie"));
        pa.setName(rs.getString("name"));
        pa.setSurname(rs.getString("surname"));
        pa.setEmail(rs.getString("email"));
        pa.setAddress(rs.getString("address"));
        pa.setEducation(rs.getString("education"));
        pa.setEntity(rs.getString("entity"));
        pa.setExperience(rs.getString("experience"));
        pa.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        pa.setCity(rs.getString("city"));
        pa.setHobbies(rs.getString("hobbies"));
        pa.setComments(rs.getString("comments"));
        pa.setCv(rs.getString("cv"));
        pa.setPhone(rs.getString("phone"));
        pa.setTypePa(rs.getString("type_pa"));
        pa.setTypeService(rs.getString("type_service"));
        pa.setGender(rs.getString("gender"));
        pa.setPostalCode(rs.getString("postal_code"));
        pa.setPaState(rs.getString("pa_state"));
        pa.setRejectionReason(rs.getString("rejection_reason"));

        return pa;
    }
}
