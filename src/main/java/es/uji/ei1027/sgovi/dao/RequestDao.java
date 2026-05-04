package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.PA;
import es.uji.ei1027.sgovi.modelo.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.time.LocalDate;
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
                        "duration, type_pa, age_pa, city, hobbies, " +
                        "required_gender, experience, education, comments) " +
                        "VALUES (?, ?, ?, ?, " +
                        "CAST(? AS type_pa_enum), " +
                        "?, ?, ?, ?, ?, ?, ?)",
                request.getOviUserId(),
                request.getRequestDate(),
                request.getStartDate(),
                request.getDuration(),
                request.getTypePa().name(),
                request.getAgePa(),
                request.getCity().name(),
                request.getHobbies().name(),
                request.getRequiredGender().name(),
                request.getExperience(),
                request.getEducation().name(),
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
                        "age_pa = ?, city = ?, hobbies = ?, required_gender = ?, " +
                        "experience = ?, education = ?, comments = ? " +
                        "WHERE id_request = ?",
                request.getStartDate(),
                request.getDuration(),
                request.getTypePa(),
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

    /* Obtiene todas las solicitudes. Devuelve una lista vacía si no hay solicitudes */
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
                            "WHERE state = ?::state_enum " +
                            "ORDER BY request_date DESC",
                    new RequestRowMapper(),
                    state
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Obtiene las solicitudes de un usuario concreto filtradas por su estado
    public List<Request> getRequestsByOviUserAndState(int oviUserId, String state) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM request WHERE ovi_user_id = ? AND state = CAST(? AS state_enum) " +
                            "ORDER BY request_date DESC",
                    new RequestRowMapper(),
                    oviUserId,
                    state
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    /* Actualiza el estado de una solicitud  */
    public void updateRequestState(int idRequest, String state, String reason) {
        jdbcTemplate.update(
                "UPDATE request SET state = ?::state_enum, comments = ? " +
                        "WHERE id_request = ?",
                state,
                reason,
                idRequest
        );
    }

    //TODO Noemí: por el momento no se puede ver el estado de la negociacion en la tabla
/*    public List<PA> findCandidatesForRequest(int idRequest) {
        try {
            Request req = getRequest(idRequest);
            return jdbcTemplate.query(
                    "SELECT pa.*, n.* FROM pa " +
                            "LEFT JOIN negotiation n ON n.pa_id = pa.id AND n.id_request = ? "+
                            "WHERE pa.city = ? AND pa.education = ? AND pa.experience = ? AND pa.hobbies = ? AND pa.gender = ? " +
                            "ORDER BY surname",
                    new PARowMapper(),
                    idRequest,
                    req.getCity(),
                    req.getEducation(),
                    req.getExperience(),
                    req.getHobbies(),
                    req.getRequiredGender()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }*/

    // Filtra los posibles candidatos PA para una request pasada por parámetro
    public List<PA> findCandidatesForRequest(int idRequest) {
        try {
            Request req = getRequest(idRequest);

            System.out.println("REQUEST >>>>>" + req.toString());
            LocalDate testDate = LocalDate.of(2026, 04, 15);

            return jdbcTemplate.query(
                    "SELECT * FROM pa " +
                            //"WHERE city = ? AND education = ? AND experience = ? AND hobbies = ? AND gender = ? " +
                            "WHERE city = ? AND education = ? AND hobbies = ? AND gender = ? " +
                            "AND experience = ? AND availability_start_date <= ? " +
                            "AND availability_end_date >= (? + (? * INTERVAL '1 month')) " +
                            "ORDER BY surname",
                    new PARowMapper(),
                    req.getCity().toString(),
                    req.getEducation().toString(),
                    req.getHobbies().toString(),
                    req.getRequiredGender().toString(),
                    req.getExperience().toString(),
                    req.getStartDate(),
                    req.getStartDate(),
                    //testDate,
                    //testDate,
                    req.getDuration()

            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
