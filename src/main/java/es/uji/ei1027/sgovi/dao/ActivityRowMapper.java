package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Activity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ActivityRowMapper implements RowMapper<Activity> {

    @Override
    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activity activity = new Activity();

        activity.setIdActivity(rs.getInt("id_activity"));
        activity.setTypeActivity(rs.getString("type_activity"));
        activity.setIdInstructor(rs.getInt("id_instructor"));
        activity.setName(rs.getString("name"));
        activity.setDescription(rs.getString("description"));
        activity.setDate(rs.getObject("date", LocalDate.class));
        activity.setPlace(rs.getString("place"));
        activity.setNumberOfParticipants((Integer) rs.getObject("number_of_participants"));
        return activity;
    }
}

