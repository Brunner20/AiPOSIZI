package com.aiposizi.lab.controller;

import com.aiposizi.lab.entity.Author;
import com.aiposizi.lab.entity.Book;
import com.aiposizi.lab.entity.Publisher;
import com.aiposizi.lab.entity.User;
import com.aiposizi.lab.service.PublisherService;
import com.aiposizi.lab.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public String getUsers(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
        List<User> userList = userService.findAll(pageNumber,ROW_PER_PAGE);

        long count = userService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("users", userList);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "user/userList";
    }

    @GetMapping(value = "/{userId}")
    public String getUserById(Model model, @PathVariable long userId){
        User user = null;
        try {
            user = userService.findById(userId);
            model.addAttribute("allowDelete",false);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find user" + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("user",user);
        return "user/user";
    }

    @GetMapping(value = {"/add"})
    public String showAddUser(Model model) {
        User user = new User();
        model.addAttribute("add",true);
        model.addAttribute("user",user);
        return "user/user-add";
    }

    @PostMapping(value = {"/add"})
    public String addUser(Model model, @ModelAttribute("user") User user) {
        return null;
    }

    @GetMapping(value = {"/{userId}/edit"})
    public String showEditUser(Model model, @PathVariable long userId) {
        User user = null;
        try {
            user = userService.findById(userId);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find user: "+ e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("add",false);
        model.addAttribute("user",user);
        return "user/user-edit";
    }

    @PostMapping(value = {"/{userId}/edit"})
    public String editUser(Model model, @PathVariable long userId, @ModelAttribute("user") User user) {
        try {
            User oldUser = userService.findById(userId);
            oldUser.setFirstname(user.getFirstname());
            oldUser.setLastname(user.getLastname());
            userService.update(oldUser);
            return "redirect:/users/" + String.valueOf(oldUser.getId());
        } catch (Exception e) {
            logger.log(Level.ERROR,"cannot update user: "+ e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("add", false);
            return "user/user-edit";
        }
    }

    @GetMapping(value = {"/{usersId}/delete"})
    public String showDeleteUser(Model model, @PathVariable long usersId) {
        User user = null;
        try {
            user = userService.findById(usersId);
            model.addAttribute("allowDelete",true);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find book" + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("user",user);
        return "user/userList";
    }

    @PostMapping(value = {"/{userId}/delete"})
    public String deleteUser(Model model, @PathVariable long userId, @ModelAttribute("user") Publisher publisher) {
        try {
            userService.deleteById(userId);
            return "redirect:users/";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.log(Level.ERROR,"cannot delete user: "+ ex.getMessage());
            model.addAttribute("errorMessage", errorMessage);

            return "user/user";
        }
    }
}
