package com.aiposizi.lab.controller;

import com.aiposizi.lab.entity.Publisher;
import com.aiposizi.lab.entity.User;
import com.aiposizi.lab.service.PublisherService;
import com.aiposizi.lab.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public String getUsers(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
        return null;
    }

    @GetMapping(value = "/users/{userId}")
    public String getUserById(Model model, @PathVariable long id){
        return null;
    }

    @GetMapping(value = {"/users/add"})
    public String showAddUser(Model model) {
        return null;
    }

    @PostMapping(value = {"/users/add"})
    public String addUser(Model model, @ModelAttribute("user") User user) {
        return null;
    }

    @GetMapping(value = {"/users/{userId}/edit"})
    public String showEditUser(Model model, @PathVariable long id) {
        return null;
    }

    @PostMapping(value = {"/users/{userId}/edit"})
    public String editUser(Model model, @PathVariable long id, @ModelAttribute("user") User user) {
        return null;
    }

    @GetMapping(value = {"/publishers/{publisherId}/delete"})
    public String showDeletePublisher(Model model, @PathVariable long id) {
        return null;
    }

    @PostMapping(value = {"/publishers/{publisherId}/delete"})
    public String deletePublisher(Model model, @PathVariable long id, @ModelAttribute("publisher") Publisher publisher) {
        return null;
    }
}
