package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.UserDetails;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDetails> {

    @Override
    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDetails user = new UserDetails();

        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRol(rs.getString("rol"));

        return user;
    }
}