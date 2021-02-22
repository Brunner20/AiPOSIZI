package com.aiposizi.lab.controller;

import com.aiposizi.lab.entity.Book;
import com.aiposizi.lab.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private BookService bookService;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        return null;
    }

    @GetMapping(value = "/books")
    public String getBooks(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
        return null;
    }

    @GetMapping(value = "/books/{bookId}")
    public String getBookById(Model model, @PathVariable long id){
        return null;
    }

    @GetMapping(value = {"/books/add"})
    public String showAddBook(Model model) {
        return null;
    }

    @PostMapping(value = {"/books/add"})
    public String addBook(Model model, @ModelAttribute("book") Book book) {
        return null;
    }

    @GetMapping(value = {"/books/{bookId}/edit"})
    public String showEditBook(Model model, @PathVariable long id) {
        return null;
    }

    @PostMapping(value = {"/books/{bookId}/edit"})
    public String editBook(Model model, @PathVariable long id, @ModelAttribute("book") Book book) {
        return null;
    }

    @GetMapping(value = {"/books/{bookId}/delete"})
    public String showDeleteBook(Model model, @PathVariable long id) {
        return null;
    }

    @PostMapping(value = {"/books/{bookId}/delete"})
    public String deleteBook(Model model, @PathVariable long id, @ModelAttribute("book") Book book) {
        return null;
    }


}
