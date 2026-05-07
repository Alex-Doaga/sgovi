package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class UserDaoJdbc implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByEmail(String email, String password, String rol) {
        try {
            String tabla = rol.equals("OviUser") ? "ovi_user" : "pa";
            // Usamos un alias para que el Mapper siempre encuentre la columna "rol"
            String sql = "SELECT email, password, '" + rol + "' as rol FROM " + tabla + " WHERE email=?";

            UserDetails user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);

            BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
            if (passwordEncryptor.checkPassword(password, user.getPassword())) {
                user.setPassword(null);
                return user;
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return null;
    }
    @Override
    public Collection<UserDetails> listAllUsers() {
        return new java.util.ArrayList<UserDetails>();
    }
}