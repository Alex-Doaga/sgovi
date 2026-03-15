package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.model.Message;
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
                "INSERT INTO message (id_message, negotiation_id, sender_id, message_text, date_msg) VALUES (?, ?, ?, ?, ?)",
                message.getIdMessage(),
                message.getNegotiationId(),
                message.getSenderId(),
                message.getMessageText(),
                message.getDateMsg()
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
                "UPDATE message SET negotiation_id=?, sender_id=?, message_text=?, date_msg=? WHERE id_message=?",
                message.getNegotiationId(),
                message.getSenderId(),
                message.getMessageText(),
                message.getDateMsg(),
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
}