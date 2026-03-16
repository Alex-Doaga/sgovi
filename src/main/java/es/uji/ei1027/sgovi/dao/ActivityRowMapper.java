package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Activity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ActivityRowMapper implements RowMapper<Activity> {

    @Override
    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activity activity = new Activity();

        activity.setIdActivity(rs.getInt("id_activity"));
        activity.setInstructorId(rs.getInt("instructor_id"));
        activity.setName(rs.getString("name"));
        activity.setDescripcion(rs.getString("descripcion"));
        activity.setDate(rs.getDate("date").toLocalDate());
        activity.setPlace(rs.getString("place"));
        activity.setNumberOfParticipants(rs.getInt("number_of_participants"));
        return activity;
    }
}