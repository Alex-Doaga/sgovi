package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.PA;
import es.uji.ei1027.sgovi.modelo.enums.*;
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
        pa.setEducation(EducationEnum.valueOf(rs.getString("education").toUpperCase()));
        pa.setExperience(Integer.valueOf(rs.getString("experience")));
        pa.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        pa.setCity(CityEnum.valueOf(rs.getString("city").toUpperCase()));
        pa.setHobbies(HobbiesEnum.valueOf(rs.getString("hobbies").toUpperCase()));
        pa.setComments(rs.getString("comments"));
        pa.setCv(rs.getString("cv"));
        pa.setPhone(rs.getString("phone"));
        pa.setTypePa(TypePaEnum.valueOf(rs.getString("type_pa").toUpperCase()));
        //pa.setTypeService(rs.getString("type_service"));
        pa.setGender(GenderEnum.valueOf(rs.getString("gender").toUpperCase()));
        pa.setPostalCode(rs.getInt("postal_code"));
        pa.setPaState(StateEnum.valueOf(rs.getString("pa_state").toUpperCase()));
        pa.setRejectionReason(rs.getString("rejection_reason"));
        pa.setAvailabilityStartDate(rs.getObject("availability_start_date", LocalDate.class));
        pa.setAvailabilityEndDate(rs.getObject("availability_end_date", LocalDate.class));

        return pa;
    }
}
