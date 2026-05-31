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
            String sql = "";
            // El técnico y el OviUser comparten la tabla física ovi_user
            if (rol.equals("OviUser") || rol.equals("Tecnico")) {
                sql = "SELECT email, password, '" + rol + "' AS rol FROM ovi_user WHERE email=?";
            } else if (rol.equals("PA")) {
                sql = "SELECT email, password, 'PA' AS rol FROM pa WHERE email=?";
            }
            UserDetails user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);

            BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
            System.out.println(passwordEncryptor.encryptPassword("12345678"));
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