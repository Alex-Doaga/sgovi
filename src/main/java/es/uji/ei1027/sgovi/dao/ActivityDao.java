package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addActivity(Activity activity) {
        jdbcTemplate.update(
                "INSERT INTO Activity (id_activity, instructor_id, name, descripcion, date, place, number_of_participants) VALUES(?, ?, ?, ?, ?, ?, ?)",
                activity.getIdActivity(),
                activity.getInstructorId(),
                activity.getName(),
                activity.getDescripcion(),
                activity.getDate(),
                activity.getPlace(),
                activity.getNumberOfParticipants()
        );
    }

    public void deleteActivity(int idActivity) {
        jdbcTemplate.update("DELETE FROM Activity WHERE id_activity = ?", idActivity);
    }

    public void updateActivity(Activity activity) {
        jdbcTemplate.update(
                "UPDATE Activity SET instructor_id = ?, name = ?, descripcion = ?, date = ?, place = ?, number_of_participants = ? WHERE id_activity = ?",
                activity.getInstructorId(),
                activity.getName(),
                activity.getDescripcion(),
                activity.getDate(),
                activity.getPlace(),
                activity.getNumberOfParticipants(),
                activity.getIdActivity()
        );
    }

    public Activity getActivity(int idActivity) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Activity WHERE id_activity = ?",
                    new ActivityRowMapper(),
                    idActivity
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // Devuelve null si no encuentra la actividad
        }
    }

    // OBTENER TODAS LAS ACTIVIDADES (Read - List)
    public List<Activity> getActivities() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Activity",
                    new ActivityRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Activity>(); // Devuelve lista vacía en vez de null para evitar NullPointerExceptions
        }
    }
}