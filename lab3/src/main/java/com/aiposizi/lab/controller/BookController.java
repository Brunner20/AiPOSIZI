package com.aiposizi.lab.controller;

import com.aiposizi.lab.dto.BookDto;
import com.aiposizi.lab.entity.Book;
import com.aiposizi.lab.service.AuthorService;
import com.aiposizi.lab.service.BookService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private static final Logger logger = LogManager.getLogger(BookController.class);

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Value("${project.title}")
    private String title;


    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {

        model.addAttribute("title",title);
        return "index";
    }

    @GetMapping(value = "/books")
    public String getBooks(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
       List<Book> bookList = bookService.findAll(pageNumber,ROW_PER_PAGE);

        long count = bookService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("books", bookList);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "book/bookList";
    }

    @GetMapping(value = "/books/{bookId}")
    public String getBookById(Model model, @PathVariable long bookId){

        Book book = null;
        try {
            book = bookService.findById(bookId);
            model.addAttribute("allowDelete",false);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find book" + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("book",book);
        return "book/book";
    }

    @GetMapping(value = {"/books/add"})
    public String showAddBook(Model model) {
        BookDto book = new BookDto();
        model.addAttribute("add",true);
        model.addAttribute("book",book);
        return "book/book-add";
    }

    @PostMapping(value = {"/books/add"})
    public String addBook(Model model, @ModelAttribute("book") BookDto bookDto) {
        try {

            Book newBook = bookService.create(bookDto);
            logger.log(Level.INFO,"book was created");
            return "redirect:/books/" + String.valueOf(newBook.getId());
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            logger.log(Level.ERROR,"cannot save book: "+ errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            return "book/book-add";
        }
    }

    @GetMapping(value = {"/books/{bookId}/edit"})
    public String showEditBook(Model model, @PathVariable long bookId) {

        Book book = null;
        try {
            book = bookService.findById(bookId);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find book: "+ e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("add",false);
        model.addAttribute("book",book);
        return "book/book-edit";
    }

    @PostMapping(value = {"/books/{bookId}/edit"})
    public String editBook(Model model, @PathVariable long bookId, @ModelAttribute("book") Book book) {

        try {
            Book oldBook = bookService.findById(bookId);
            oldBook.setYear(book.getYear());
            oldBook.setTitle(book.getTitle());
            bookService.update(oldBook);
            logger.log(Level.INFO,"book was updated");
            return "redirect:/books/" + String.valueOf(oldBook.getId());
        } catch (Exception e) {
            logger.log(Level.ERROR,"cannot update book: "+ e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("add", false);
            return "book/book-edit";
        }

    }

    @GetMapping(value = "/books/{bookId}/delete")
    public String deleteBookById(Model model, @PathVariable long bookId){

        Book book = null;
        try {
            book = bookService.findById(bookId);
            model.addAttribute("allowDelete",true);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find book" + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("book",book);
        return "book/book";
    }

    @PostMapping(value = {"/books/{bookId}/delete"})
    public String deleteBook(Model model, @PathVariable long bookId) {
        try {
            bookService.deleteById(bookId);
            logger.log(Level.INFO,"book was deleted");
            return "redirect:/books";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.log(Level.ERROR,"cannot delete book: "+ ex.getMessage());
            model.addAttribute("errorMessage", errorMessage);

            return "book/book";
        }
    }


}
