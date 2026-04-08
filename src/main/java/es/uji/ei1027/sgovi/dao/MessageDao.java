package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class MessageDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* CREATE: Añade un nuevo mensaje a la base de datos */
    public void addMessage(Message message) {
        jdbcTemplate.update(
                "INSERT INTO message (id_negotiation, sender_type, message_text) " +
                        "VALUES (?, ?, ?)",
                message.getIdNegotiation(),
                message.getSenderType(),
                message.getMessageText()
        );
    }

    /* DELETE: Elimina un mensaje */
    public void deleteMessage(int idMessage) {
        jdbcTemplate.update(
                "DELETE FROM message WHERE id_message=?",
                idMessage
        );
    }

    /* UPDATE: Modifica un mensaje completo */
    public void updateMessage(Message message) {
        jdbcTemplate.update(
                "UPDATE message SET message_text=? WHERE id_message=?",
                message.getMessageText(),
                message.getIdMessage()
        );
    }

    /* READ: Obtiene un mensaje por su ID. Devuelve null si no existe */
    public Message getMessage(int idMessage) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM message WHERE id_message=?",
                    new MessageRowMapper(),
                    idMessage
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtiene todos los mensajes. Devuelve una lista vacía si no hay mensajes */
    public List<Message> getMessages() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM message",
                    new MessageRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Message>();
        }
    }

    // Obtiene todos los mensajes de una negociación
    public List<Message> getMessagesByNegotiation(int idNegotiation) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM message WHERE id_negotiation = ? " +
                            "ORDER BY date_msg ASC",
                    new MessageRowMapper(),
                    idNegotiation
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Borrar todos los mensajes de una negociación
    public void deleteMessagesByNegotiation(int idNegotiation) {
        jdbcTemplate.update(
                "DELETE FROM message WHERE id_negotiation = ?",
                idNegotiation
        );
    }


}