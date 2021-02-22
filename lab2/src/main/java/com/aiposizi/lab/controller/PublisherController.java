package com.aiposizi.lab.controller;


import com.aiposizi.lab.entity.Publisher;
import com.aiposizi.lab.service.PublisherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PublisherController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private PublisherService publisherService;

    @GetMapping(value = "/publishers")
    public String getPublishers(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
        return null;
    }

    @GetMapping(value = "/publishers/{publisherId}")
    public String getPublisherById(Model model, @PathVariable long id){
        return null;
    }

    @GetMapping(value = {"/publishers/add"})
    public String showAddPublisher(Model model) {
        return null;
    }

    @PostMapping(value = {"/publishers/add"})
    public String addPublisher(Model model, @ModelAttribute("publisher") Publisher publisher) {
        return null;
    }

    @GetMapping(value = {"/publishers/{publisherId}/edit"})
    public String showEditPublisher(Model model, @PathVariable long id) {
        return null;
    }

    @PostMapping(value = {"/publishers/{publisherId}/edit"})
    public String editPublisher(Model model, @PathVariable long id, @ModelAttribute("publisher") Publisher publisher) {
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
