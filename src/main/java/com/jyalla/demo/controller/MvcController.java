package com.jyalla.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.jyalla.demo.modal.User;

@Controller
@RequestMapping("/mvc")
public class MvcController {

    @Autowired
    UserRestController userController;

    @RequestMapping("/hello")
    public String hello(@RequestParam(name = "pNo", defaultValue = "0") int pNo, Model model) {
        // List<User> allUsers =
        int size = userController.getUsers()
                .getBody()
                .size();
        int requiredPages = (int) Math.ceil(size / (double) 5);
        Page<User> data = userController.getAllUsersPagination(pNo, 5)
                .getBody();
        List<User> userList = new ArrayList<User>();
        if (data != null && data.hasContent()) {
            userList = data.getContent();
        }

        model.addAttribute("msg", "Other Side");
        model.addAttribute("users", userList);
        model.addAttribute("requiredPages", requiredPages);
        return "view";
    }

    @RequestMapping("/addUser")
    public String addUser() {
        return "addUser";
    }

    @RequestMapping("/saveUser")
    public ModelAndView saveUser(@ModelAttribute("user") User user) {
        System.out.println(user);
        userController.saveUser(user);
        return new ModelAndView("redirect:/mvc/hello");
    }

    @RequestMapping("/delete")
    public ModelAndView deleteUser(@RequestParam("id") String uuid) {
        userController.deleteUser(UUID.fromString(uuid));
        return new ModelAndView("redirect:/mvc/hello");
    }

}
