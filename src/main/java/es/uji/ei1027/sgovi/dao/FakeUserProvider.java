package es.uji.ei1027.sgovi.dao;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import es.uji.ei1027.sgovi.modelo.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;

@Repository
public class FakeUserProvider implements UserDao {
    final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();

    public FakeUserProvider() {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        // --- USUARIO ALICE (TIPO PA) ---
        UserDetails userAlice = new UserDetails();
        userAlice.setUsername("alice");
        userAlice.setPassword(passwordEncryptor.encryptPassword("alice"));
        userAlice.setRol("PA"); // Asignamos el tipo
        knownUsers.put("alice", userAlice);

        // --- USUARIO BOB (TIPO OviUser) ---
        UserDetails userBob = new UserDetails();
        userBob.setUsername("bob");
        userBob.setPassword(passwordEncryptor.encryptPassword("bob"));
        userBob.setRol("OviUser"); // Asignamos el tipo
        knownUsers.put("bob", userBob);
    }

    @Override
    public UserDetails loadUserByUsername(String username, String password,String rol) {
        UserDetails user = knownUsers.get(username.trim());
        if (user == null) {
            return null; // Usuari no trobat
        }

        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Per seguretat el camp password no s’ha de tornar
            UserDetails safeUser = new UserDetails();
            safeUser.setUsername(user.getUsername());

            // IMPORTANTE: Pasar el userType al usuario seguro
            safeUser.setRol(user.getRol());

            return safeUser;
        } else {
            return null; // bad login!
        }
    }

    @Override
    public Collection<UserDetails> listAllUsers() {
        return knownUsers.values();
    }
}

