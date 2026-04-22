package es.uji.ei1027.sgovi.dao;

import es.uji.ei1027.sgovi.modelo.UserDetails;

import java.util.Collection;

public interface UserDao {
    UserDetails loadUserByUsername(String username,String password);
    Collection<UserDetails> listAllUsers();
}
