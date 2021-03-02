package com.aiposizi.lab.controller;

import com.aiposizi.lab.dto.AuthorDto;
import com.aiposizi.lab.entity.Author;
import com.aiposizi.lab.service.AuthorService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/authors")
public class AuthorController {

    private static final Logger logger = LogManager.getLogger(AuthorController.class);

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private AuthorService authorService;

    @GetMapping(value = "")
    public String getAuthors(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
        List<Author> authorList = authorService.findAll(pageNumber,ROW_PER_PAGE);

        long count = authorService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("authors", authorList);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "author/authorList";
    }

    @GetMapping(value = "/{authorId}")
    public String getAuthorById(Model model, @PathVariable long authorId){
        Author author = null;
        try {
            author = authorService.findById(authorId);
            model.addAttribute("allowDelete",false);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find author" + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("author",author);
        return "author/author";
    }

    @GetMapping(value = {"/add"})
    public String showAddAuthor(Model model) {
        AuthorDto author = new AuthorDto();
        model.addAttribute("add",true);
        model.addAttribute("author",author);
        return "author/author-add";
    }

    @PostMapping(value = {"/add"})
    public String addAuthor(Model model, @ModelAttribute("author") @Valid AuthorDto author) {
        try {
            Author newAuthor = authorService.save(author);
            logger.log(Level.INFO,"author was created");
            return "redirect:/authors/" + String.valueOf(newAuthor.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.log(Level.ERROR,errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            return "author/author-edit";
        }
    }

    @GetMapping(value = {"/{authorId}/edit"})
    public String showEditAuthor(Model model, @PathVariable long authorId) {
        Author author = null;
        try {
            author = authorService.findById(authorId);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find author: "+ e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("add",false);
        model.addAttribute("author",author);
        return "author/author-edit";
    }

    @PostMapping(value = {"/{authorId}/edit"})
    public String editAuthor(Model model, @PathVariable long authorId, @ModelAttribute("author") Author author) {
        try {
            Author oldAuthor = authorService.findById(authorId);
            oldAuthor.setFirstname(author.getFirstname());
            oldAuthor.setLastname(author.getLastname());
            oldAuthor.setYear(author.getYear());
            authorService.update(oldAuthor);
            logger.log(Level.INFO,"author was updated");
            return "redirect:/authors/" + String.valueOf(oldAuthor.getId());
        } catch (Exception e) {
            logger.log(Level.ERROR,"cannot update author: "+ e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("add", false);
            return "author/author-edit";
        }
    }

    @GetMapping(value = {"/{authorId}/delete"})
    public String showDeleteAuthor(Model model, @PathVariable long authorId) {
        Author author = null;

        try {
            author = authorService.findById(authorId);
            model.addAttribute("allowDelete",true);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find author" + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("author",author);
        return "author/author";
    }

    @PostMapping(value = {"/{authorId}/delete"})
    public String deleteAuthor(Model model, @PathVariable long authorId, @ModelAttribute("author") Author author) {
        try {
            authorService.deleteById(authorId);
            logger.log(Level.INFO,"author was deleted");
            return "redirect:/authors/";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.log(Level.ERROR,"cannot delete author: "+ ex.getMessage());
            model.addAttribute("errorMessage", errorMessage);

            return "author/author";
        }
    }
}
