package com.jyalla.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.exception.UserNotFoundException;
import com.jyalla.demo.messaging.MQUtil;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.BlogService;
import com.jyalla.demo.service.UserService;


@TestMethodOrder(OrderAnnotation.class)
class UserControllerMockTest extends BaseClass {


    public static Logger logger = LoggerFactory.getLogger(UserControllerMockTest.class);

    @Mock
    UserService userService;

    @Mock
    BlogService blogService;

    @Mock
    MQUtil mqUtil;

    @InjectMocks
    UserRestController userController;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    void getUsers() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        User user2 = new User(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "dummyUser2", "dummy2@email.com", "4321", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        when(userService.getAllUsers()).thenReturn(List.of(user, user2));

        ResponseEntity<List<User>> users = userController.getUsers();
        logger.info("getUsers {}", users);
        assertEquals(2, userController.getUsers()
                .getBody()
                .size());
    }

    @Test
    @Order(3)
    void getSingleUser() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        when(userService.getSingleUser(any(UUID.class))).thenReturn(user);
        ResponseEntity<User> oneUser = userController.getOneUser(user.getId());
        logger.info("SingleUser is {}", oneUser);
        assertEquals(true, userController.getUsers()
                .getStatusCode()
                .is2xxSuccessful());
    }


    @Test
    @Order(2)
    void postUser() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(), "dummyUser", 2);
        logger.info(user.getPassword());
        when(userService.save(user)).thenReturn(user);
        when(userService.getSingleUser(user.getId())).thenReturn(user);
        when(mqUtil.publishMessage(Mockito.any())).thenReturn(true);

        ResponseEntity<Object> saveUser = userController.saveUser(user);
        logger.info(saveUser.toString());
        // ResponseEntity<Object> saveUser = userController.saveUser(new UserDto(user.getId(),
        // user.getUsername(), user.getEmail(), user.getPhoneNo(),
        // user.getProfilePic(),user.getStatus(), user.getCreatedBy(), user.getCreatedOn(),
        // user.getEncodedPassword(), user.getRole()));
        assertEquals(true, saveUser.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(4)
    void putUser() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        when(userService.save(user)).thenReturn(user);
        when(userService.getSingleUser(user.getId())).thenReturn(user);
        when(mqUtil.publishMessageMultiple(Mockito.any())).thenReturn(true);
        ResponseEntity<Object> saveUser = userController.updateUser(user, user.getId());
        assertEquals(true, saveUser.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(5)
    void deleteUser() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        doNothing().when(userService)
                .delete(user);
        when(userService.getSingleUser(any(UUID.class))).thenReturn(user);
        ResponseEntity<Object> deleteUser = userController.deleteUser(user.getId());
        assertEquals(true, deleteUser.getStatusCode()
                .is2xxSuccessful());
    }

    @Test()
    @Order(6)
    void userNotFound() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        // when(userService.getSingleUser(any(UUID.class))).thenThrow(new UserNotFoundException());
        when(userService.getSingleUser(any(UUID.class))).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userController.deleteUser(user.getId()));
    }
}
