package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Inscription;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InscriptionRowMapper implements RowMapper<Inscription> {

    @Override
    public Inscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inscription inscription = new Inscription();

        inscription.setIdInscription(rs.getInt("id_inscription"));
        inscription.setActivityId(rs.getInt("activity_id"));
        inscription.setOviUserId(rs.getInt("ovi_user_id"));
        inscription.setPaId(rs.getInt("pa_id"));
        inscription.setDate(rs.getDate("date").toLocalDate());


        return inscription;
    }
}