package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class RequestDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* CREATE: Añade una nueva solicitud a la base de datos */
    public void addRequest(Request request) {
        jdbcTemplate.update(
                "INSERT INTO request (ovi_user_id, request_date, start_date, " +
                        "duration, type_pa, type_service, age_pa, city, hobbies, " +
                        "required_gender, experience, education, comments) " +
                        "VALUES (?, ?, ?, ?, " +
                        "CAST(? AS type_pa_enum), " +
                        "CAST(? AS type_accompaniment_enum), " +
                        "?, ?, ?, ?, ?, ?, ?)",
                request.getOviUserId(),
                request.getRequestDate(),
                request.getStartDate(),
                request.getDuration(),
                request.getTypePa(),
                request.getTypeService(),
                request.getAgePa(),
                request.getCity(),
                request.getHobbies(),
                request.getRequiredGender(),
                request.getExperience(),
                request.getEducation(),
                request.getComments()
        );
    }

    /* DELETE: Elimina una solicitud */
    public void deleteRequest(int idRequest) {
        jdbcTemplate.update(
                "DELETE FROM request WHERE id_request=?", idRequest
        );
    }

    /* UPDATE: Modifica una solicitud completa */
    public void updateRequest(Request request) {
        jdbcTemplate.update(
                "UPDATE request SET " +
                        "start_date = ?, duration = ?, " +
                        "type_pa = CAST(? AS type_pa_enum), " +
                        "type_service = CAST(? AS type_accompaniment_enum), " +
                        "age_pa = ?, city = ?, hobbies = ?, required_gender = ?, " +
                        "experience = ?, education = ?, comments = ? " +
                        "WHERE id_request = ?",
                request.getStartDate(),
                request.getDuration(),
                request.getTypePa(),
                request.getTypeService(),
                request.getAgePa(),
                request.getCity(),
                request.getHobbies(),
                request.getRequiredGender(),
                request.getExperience(),
                request.getEducation(),
                request.getComments(),
                request.getIdRequest()
        );
    }

    /* READ: Obtiene una solicitud por su ID. Devuelve null si no existe */
    public Request getRequest(int idRequest) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM request WHERE id_request=?",
                    new RequestRowMapper(),
                    idRequest
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtiene todos las solicitudes. Devuelve una lista vacía si no hay solicitudes */
    public List<Request> getRequests() {
        try {
            return jdbcTemplate.query("SELECT * FROM request",
                    new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

    // Obtiene todas las solicitudes de un usuario
    public List<Request> getRequestsByOviUser(int oviUserId) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM request WHERE ovi_user_id = ? " +
                            "ORDER BY request_date DESC",
                    new RequestRowMapper(),
                    oviUserId
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Obtiene las solicitudes con un estado(pending/accepted/refused/)
    public List<Request> getRequestsByState(String state) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM request " +
                            "WHERE state = CAST(? AS state_enum) " +
                            "ORDER BY request_date DESC",
                    new RequestRowMapper(),
                    state
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }


    /* Actualiza el estado de una solicitud  */
    public void updateRequestState(int idRequest, String state) {
        jdbcTemplate.update(
                "UPDATE request SET state = CAST(? AS state_enum) " +
                        "WHERE id_request = ?",
                state,
                idRequest
        );
    }
}
