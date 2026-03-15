package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.model.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {

        Message message = new Message();

        message.setIdMessage(rs.getInt("id_message"));
        message.setNegotiationId(rs.getInt("negotiation_id"));
        message.setSenderId(rs.getInt("sender_id"));
        message.setMessageText(rs.getString("message_text"));
        message.setDateMsg(rs.getDate("date_msg").toLocalDate());

        return message;
    }
}