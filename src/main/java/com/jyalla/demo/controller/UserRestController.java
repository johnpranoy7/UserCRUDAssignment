package com.jyalla.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jyalla.demo.exception.UserNotFoundException;
import com.jyalla.demo.messaging.CustomMessage;
import com.jyalla.demo.messaging.MQUtil;
import com.jyalla.demo.messaging.MessageNotSentException;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;


@RestController
// @Validated
@RequestMapping(path = "/rest")
public class UserRestController {

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserService userService;

    @Autowired
    MQUtil mqUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String userNotFoundMessg = "Userid not Found ";
    private String adminName = "Admin";

    @GetMapping(path = "/User")
    public ResponseEntity<List<User>> getUsers() {
        logger.info("getUsers() is Executed");
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/User/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable("id") UUID id) {
        logger.info("getOneUser() {} is Executed", id);
        var singleUser = userService.getSingleUser(id);
        logger.info("singleUser= {}", singleUser);
        if (singleUser == null)
            throw new UserNotFoundException(userNotFoundMessg + id);
        return new ResponseEntity<>(singleUser, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path = "/User")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid User emp) {
        logger.info("saveUser() {} is Executed", emp);
        // User user = new User(emp.getUsername(), emp.getEmail(), emp.getPhoneNo(),
        // emp.getProfilePic(), emp.isStatus(), emp.getEncodedPassword(), emp.getRole());
        User user;
        logger.info("emp.getPassword() is {}", emp.getPassword());
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        logger.info("emp.getPassword() is {}", emp.getPassword());
        emp.setUpdatedBy(adminName);
        emp.setUpdatedOn(new Date());
        emp.setCreatedBy(adminName);
        emp.setCreatedOn(new Date());

        user = userService.save(emp);

        CustomMessage message = new CustomMessage();
        message.setMessageBody("Saved the User with ID- " + user.getId() + " and Name: " + user.getUsername());
        boolean publishMessage = mqUtil.publishMessage(message);
        if (!publishMessage)
            throw new MessageNotSentException();
        logger.info("PublishMessage was {}", publishMessage);

        logger.info("saved User is {}", emp);
        user.setPassword("Nulll");
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.CREATED);

    }

    @PutMapping(path = "/User/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid User user, @PathVariable("id") UUID id) {
        logger.info("updateUser() is Executed");
        var singleUser = userService.getSingleUser(id);
        if (singleUser != null) {
            logger.info("Found existing User for PUT {}", singleUser);
            singleUser.setEmail(user.getEmail());
            singleUser.setUsername(user.getUsername());
            singleUser.setPhoneNo(user.getPhoneNo());
            singleUser.setProfilePic(user.getProfilePic());
            singleUser.setStatus(user.getStatus());
            singleUser.setUpdatedBy(adminName);
            singleUser.setUpdatedOn(new Date());
            userService.save(singleUser);

            CustomMessage message = new CustomMessage();
            message.setMessageBody("Modified the User with ID- " + user.getId() + " and Name: " + user.getUsername());
            boolean publishMessage = mqUtil.publishMessageMultiple(message);
            if (!publishMessage)
                throw new MessageNotSentException();
            logger.info("PublishMessage was {}", publishMessage);

            return new ResponseEntity<>(singleUser, new HttpHeaders(), HttpStatus.ACCEPTED);

        } else {
            throw new UserNotFoundException(userNotFoundMessg + id);
        }
    }

    @DeleteMapping(path = "/User/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID id) {
        logger.info("deleteUser() is Executed");
        var delUser = userService.getSingleUser(id);
        logger.info("deleted user is {}", delUser);
        if (delUser == null)
            throw new UserNotFoundException(userNotFoundMessg + id);
        try {
            userService.delete(delUser);
        } catch (Exception e) {
            logger.error("error Deleting the User {}", delUser);
            return new ResponseEntity<>(delUser, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(delUser, new HttpHeaders(), HttpStatus.OK);

    }
}
