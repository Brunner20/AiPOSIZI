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
@RequestMapping(value = "/users")
public class UserController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String getUsers(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
        return null;
    }

    @GetMapping(value = "/{userId}")
    public String getUserById(Model model, @PathVariable long userId){
        return null;
    }

    @GetMapping(value = {"/add"})
    public String showAddUser(Model model) {
        return null;
    }

    @PostMapping(value = {"/add"})
    public String addUser(Model model, @ModelAttribute("user") User user) {
        return null;
    }

    @GetMapping(value = {"/{userId}/edit"})
    public String showEditUser(Model model, @PathVariable long userId) {
        return null;
    }

    @PostMapping(value = {"/{userId}/edit"})
    public String editUser(Model model, @PathVariable long userId, @ModelAttribute("user") User user) {
        return null;
    }

    @GetMapping(value = {"/{usersId}/delete"})
    public String showDeleteUser(Model model, @PathVariable long usersId) {
        return null;
    }

    @PostMapping(value = {"/{userId}/delete"})
    public String deleteUser(Model model, @PathVariable long userId, @ModelAttribute("user") Publisher publisher) {
        return null;
    }
}
