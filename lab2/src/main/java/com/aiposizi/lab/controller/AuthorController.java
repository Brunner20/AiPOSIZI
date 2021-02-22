package com.aiposizi.lab.controller;

import com.aiposizi.lab.entity.Author;
import com.aiposizi.lab.service.AuthorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private AuthorService authorService;

    @GetMapping(value = "/authors")
    public String getAuthors(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
        return null;
    }

    @GetMapping(value = "/authors/{authorId}")
    public String getAuthorById(Model model, @PathVariable long id){
        return null;
    }

    @GetMapping(value = {"/authors/add"})
    public String showAddAuthor(Model model) {
        return null;
    }

    @PostMapping(value = {"/authors/add"})
    public String addAuthor(Model model, @ModelAttribute("author") Author author) {
        return null;
    }

    @GetMapping(value = {"/authors/{authorId}/edit"})
    public String showEditAuthor(Model model, @PathVariable long id) {
        return null;
    }

    @PostMapping(value = {"/authors/{authorId}/edit"})
    public String editAuthor(Model model, @PathVariable long id, @ModelAttribute("author") Author author) {
        return null;
    }

    @GetMapping(value = {"/authors/{authorId}/delete"})
    public String showDeleteAuthor(Model model, @PathVariable long id) {
        return null;
    }

    @PostMapping(value = {"/authors/{authorId}/delete"})
    public String deleteAuthor(Model model, @PathVariable long id, @ModelAttribute("author") Author author) {
        return null;
    }
}
