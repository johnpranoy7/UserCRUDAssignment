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
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;


@RestController
@RequestMapping(path = "/rest")
public class UserRestController {

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserService userService;

    @GetMapping(path = "/User")
    public List<User> getUsers() {
        logger.info("getUsers() is Executed");
        return userService.getAllUsers();
    }

    @GetMapping(path = "/User/{id}")
    public User getOneUser(@PathVariable("id") UUID id) {
        logger.info("getOneUser() " + id + " is Executed");
        return userService.getSingleUser(id);
    }

    @PostMapping(path = "/User")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid User emp) {
        logger.info("saveUser() " + emp + " is Executed");
        emp.setUpdatedBy("Admin");
        emp.setUpdatedOn(new Date());
        emp.setCreatedBy("Admin");
        emp.setCreatedOn(new Date());
        User user;
        try {
            user = userService.save(emp);
            logger.info("saved User is " + user);
            return new ResponseEntity<Object>("Created Successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while saving User" + e);
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/User/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid User emp, @PathVariable("id") int id) {
        logger.info("updateUser() is Executed");
        emp.setUpdatedBy("Admin");
        emp.setUpdatedOn(new Date());
        User user = userService.save(emp);
        logger.info("saved user is" + user);
        if (user != null)
            return ResponseEntity.status(HttpStatus.CREATED)
                    .build();
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
    }

    @DeleteMapping(path = "/User/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID id) {
        logger.info("deleteUser() is Executed");
        User delUser = userService.getSingleUser(id);
        logger.info("deleted user is" + delUser);
        try {
            userService.delete(delUser);
        } catch (Exception e) {
            logger.error("error Deleting the User" + delUser);
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);

    }
}
