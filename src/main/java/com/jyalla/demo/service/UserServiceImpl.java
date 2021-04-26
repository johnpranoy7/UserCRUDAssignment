package com.jyalla.demo.service;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyalla.demo.controller.UserRestController;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Override
    public List<User> getAllUsers() {
        logger.info("getAllUsers() is Executed");
        return userRepository.findAll();
    }

    @Override
    public User getSingleUser(UUID id) {
        logger.info("getSingleUser() is Executed");
        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public User save(User user) {
        logger.info("save() is Executed");
        return userRepository.save(user);

    }

    @Override
    public void delete(User delUser) {
        logger.info("delete() is Executed");
        userRepository.delete(delUser);
    }

}
