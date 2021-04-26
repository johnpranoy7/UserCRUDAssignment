package com.jyalla.demo;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;

public class UserServiceTest extends BaseClass {

    @Autowired
    UserService userService;

    @Test
    public void getAllUsers() {
        assertTrue(userService.getAllUsers()
                .size() > 0);
    }

    @Test
    public void getSingleUser() {
        User user = new User();
        System.out.println(user);
        assertTrue(userService.getSingleUser(user.getId())
                .equals(user));
    }

    @Test
    public void saveUser() {
        User user = new User();
        assertTrue(userService.save(user) != null);
    }

    @Test
    public void deleteUser() {
        User user = new User();
        userService.delete(user);
        assertTrue(user.getId() == null);
    }
}
