package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.OviUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class OviUserRowMapper implements RowMapper<OviUser> {

    public OviUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        OviUser oviUser = new OviUser();
        oviUser.setIdOviUser(rs.getInt("id_ovi_user"));
        oviUser.setName(rs.getString("name"));
        oviUser.setSurname(rs.getString("surname"));
        oviUser.setDniNie(rs.getString("dni_nie"));
        oviUser.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        oviUser.setAddress(rs.getString("address"));
        oviUser.setCity(rs.getString("city"));
        oviUser.setPostalCode(rs.getString("postal_code"));
        oviUser.setEmail(rs.getString("email"));
        oviUser.setPhone(rs.getString("phone"));
        oviUser.setEntity(rs.getString("entity"));
        oviUser.setNameTutor(rs.getString("name_tutor"));
        oviUser.setDniNieTutor(rs.getString("dni_nie_tutor"));
        oviUser.setHasDepenDegree(rs.getBoolean("has_depen_degree"));
        oviUser.setDepenDegree((Integer) rs.getObject("depen_degree"));
        oviUser.setProjectLifeDoc(rs.getString("project_life_doc"));
        oviUser.setSocialServiceCenter(rs.getString("social_service_center"));
        oviUser.setOviUserState(rs.getString("ovi_user_state"));
        oviUser.setRejectionReason(rs.getString("rejection_reason"));
        return oviUser;
    }

}
