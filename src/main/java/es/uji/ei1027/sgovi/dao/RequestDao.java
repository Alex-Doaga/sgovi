package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Request;
import es.uji.ei1027.sgovi.modelo.enums.State;
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
                "INSERT INTO request ( ovi_user_id, request_date, start_date, duration, type_pa, type_service, age_pa, hobbies, required_gender, schedule, state, comments) " +
                        "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                request.getOviUserId(), request.getRequestDate(), request.getStartDate(),
                request.getDuration(), request.getTypePA().name(), request.getTypeService().name(),
                request.getAgePA(), request.getHobbies(), request.getRequiredGender(),
                request.getSchedule(), request.getState().name(), request.getComments()
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
                "UPDATE request SET ovi_user_id=?, request_date=?, start_date=?, duration=?, type_pa=?, type_service=?, age_pa=?, hobbies=?, required_gender=?, schedule=?, state=?, comments=? " +
                        "WHERE id_request=?",
                request.getOviUserId(), request.getRequestDate(), request.getStartDate(),
                request.getDuration(), request.getTypePA().name(), request.getTypeService().name(),
                request.getAgePA(), request.getHobbies(), request.getRequiredGender(),
                request.getSchedule(), request.getState().name(), request.getComments(),
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

    /* Actualiza el estado de una solicitud  */
    public void updateRequestStatus(int idRequest, State newState) {
        jdbcTemplate.update(
                "UPDATE request SET state=? WHERE id_request=?",
                newState.name(), idRequest
        );
    }
}
