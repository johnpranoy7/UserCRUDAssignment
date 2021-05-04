package com.jyalla.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jyalla.demo.exception.UserNotFoundException;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;


@RestController
// @Validated
@RequestMapping(path = "/rest")
public class UserRestController {

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserService userService;

    @GetMapping(path = "/User")
    public ResponseEntity<List<User>> getUsers() {
        logger.info("getUsers() is Executed");
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping(path = "/User/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable("id") UUID id) {
        logger.info("getOneUser() " + id.toString() + " is Executed");
        User singleUser = userService.getSingleUser(id);
        logger.info("singleUser=" + singleUser);
        if (singleUser == null)
            throw new UserNotFoundException("Userid not Found " + id);
        return new ResponseEntity<User>(singleUser, HttpStatus.OK);
    }

    @PostMapping(path = "/User")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid User emp) {
        logger.info("saveUser() " + emp + " is Executed");
        emp.setUpdatedBy("Admin");
        emp.setUpdatedOn(new Date());
        emp.setCreatedBy("Admin");
        emp.setCreatedOn(new Date());
        User user;
        user = userService.save(emp);
        logger.info("saved User is " + user);
        return new ResponseEntity<Object>(user, HttpStatus.CREATED);

    }

    @PutMapping(path = "/User/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid User emp, @PathVariable("id") UUID id) {
        logger.info("updateUser() is Executed");
        User user;
        User singleUser = userService.getSingleUser(id);
        if (singleUser != null) {
            logger.info("Found existing User for PUT" + singleUser);
            singleUser.setEmail(emp.getEmail());
            singleUser.setUsername(emp.getUsername());
            singleUser.setPhoneNo(emp.getPhoneNo());
            singleUser.setProfilePic(emp.getProfilePic());
            singleUser.setStatus(emp.getStatus());
            singleUser.setUpdatedBy("Admin");
            singleUser.setUpdatedOn(new Date());
            user = userService.save(singleUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .build();
        } else {
            throw new UserNotFoundException("Userid not Found " + id);
        }
    }

    @DeleteMapping(path = "/User/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID id) {
        logger.info("deleteUser() is Executed");
        User delUser = userService.getSingleUser(id);
        logger.info("deleted user is" + delUser);
        if (delUser == null)
            throw new UserNotFoundException("Userid not Found " + id);
        try {
            userService.delete(delUser);
        } catch (Exception e) {
            logger.error("error Deleting the User" + delUser);
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);

    }
}
