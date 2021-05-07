package com.jyalla.demo.service;

import java.util.List;
import java.util.UUID;
import com.jyalla.demo.modal.User;


public interface UserService {

    List<User> getAllUsers();

    User getSingleUser(UUID id);

    User save(User user);

    void delete(User delUser);

    public List<User> findByEmail(String email);

    User findByUsername(String username);

}
