package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.Inscription;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class InscriptionRowMapper implements RowMapper<Inscription> {

    @Override
    public Inscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inscription inscription = new Inscription();

        inscription.setIdInscription(rs.getInt("id_inscription"));
        inscription.setIdActivity(rs.getInt("id_activity"));
        inscription.setIdOviUser((Integer) rs.getObject("id_ovi_user"));
        inscription.setIdPa((Integer) rs.getObject("id_pa"));
        inscription.setDate(rs.getObject("date", LocalDate.class));
        inscription.setHasAttended(rs.getBoolean("has_attended"));


        return inscription;
    }
}