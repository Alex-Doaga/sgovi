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
                "INSERT INTO activity (type_activity, id_instructor, name, description, " +
                        "date, place, number_of_participants) " +
                        "VALUES (CAST(? AS type_activity_enum), ?, ?, ?, ?, ?, ?)",
                activity.getTypeActivity(),
                activity.getIdInstructor(),
                activity.getName(),
                activity.getDescription(),
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
                "UPDATE activity SET " +
                        "type_activity = CAST(? AS type_activity_enum), " +
                        "id_instructor = ?, name = ?, description = ?, " +
                        "date = ?, place = ?, number_of_participants = ? " +
                        "WHERE id_activity = ?",
                activity.getTypeActivity(),
                activity.getIdInstructor(),
                activity.getName(),
                activity.getDescription(),
                activity.getDate(),
                activity.getPlace(),
                activity.getNumberOfParticipants(),
                activity.getIdActivity()
        );
    }

    public Activity getActivity(int idActivity) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM activity WHERE id_activity = ?",
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
                    "SELECT * FROM activity",
                    new ActivityRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Activity>(); // Devuelve lista vacía en vez de null para evitar NullPointerExceptions
        }
    }

    // Muestra actividades de Formación o Divulgación
    public List<Activity> getActivitiesByType(String typeActivity) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM activity " +
                            "WHERE type_activity = CAST(? AS type_activity_enum) " +
                            "ORDER BY date DESC",
                    new ActivityRowMapper(),
                    typeActivity
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Muestra el historias de actividades de un instructor
    public List<Activity> getActivitiesByInstructor(int idInstructor) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM activity WHERE id_instructor = ? " +
                            "ORDER BY date DESC",
                    new ActivityRowMapper(),
                    idInstructor
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }


}