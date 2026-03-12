package es.uji.ei1027.sgovi.dao;
import es.uji.ei1027.sgovi.modelo.Request;
import es.uji.ei1027.sgovi.modelo.State;
import es.uji.ei1027.sgovi.modelo.TypeAccompaniment;
import es.uji.ei1027.sgovi.modelo.TypePa;
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
        request.setTypePA(TypePa.valueOf(rs.getString("type_pa")));
        request.setTypeService(TypeAccompaniment.valueOf(rs.getString("type_service")));
        request.setState(State.valueOf(rs.getString("state")));
        request.setAgePA(rs.getInt("age_pa"));
        request.setHobbies(rs.getString("hobbies"));
        request.setRequiredGender(rs.getString("required_gender"));
        request.setSchedule(rs.getString("schedule"));
        request.setComments(rs.getString("comments"));
        return request;
    }

}
