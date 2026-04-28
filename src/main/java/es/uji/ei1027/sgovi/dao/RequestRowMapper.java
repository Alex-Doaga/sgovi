package es.uji.ei1027.sgovi.dao;
import es.uji.ei1027.sgovi.modelo.Request;
import es.uji.ei1027.sgovi.modelo.enums.*;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class RequestRowMapper implements RowMapper<Request> {

    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {

        Request request = new Request();

        request.setIdRequest(rs.getInt("id_request"));
        request.setOviUserId(rs.getInt("ovi_user_id"));
        request.setRequestDate(rs.getObject("request_date", LocalDate.class));
        request.setStartDate(rs.getObject("start_date", LocalDate.class));
        request.setDuration(rs.getInt("duration"));
        request.setTypePa(TypePaEnum.valueOf(rs.getString("type_pa")));
        request.setAgePa(rs.getInt("age_pa"));
        request.setCity(CityEnum.valueOf(rs.getString("city")));
        request.setHobbies(HobbiesEnum.valueOf(rs.getString("hobbies")));
        request.setRequiredGender(GenderEnum.valueOf(rs.getString("required_gender")));
        request.setExperience(rs.getInt("experience"));
        request.setEducation(EducationEnum.valueOf(rs.getString("education")));
        request.setState(StateEnum.valueOf(rs.getString("state")));
        request.setComments(rs.getString("comments"));

        return request;
    }

}
