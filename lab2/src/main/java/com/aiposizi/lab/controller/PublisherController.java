package com.aiposizi.lab.controller;


import com.aiposizi.lab.dto.BookDto;
import com.aiposizi.lab.entity.Book;
import com.aiposizi.lab.entity.Publisher;
import com.aiposizi.lab.entity.User;
import com.aiposizi.lab.service.PublisherService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/publishers")
public class PublisherController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private PublisherService publisherService;

    @GetMapping(value = "")
    public String getPublishers(Model model, @RequestParam(value = "page",defaultValue = "1") int pageNumber){
        List<Publisher> publisherList = publisherService.findAll(pageNumber,ROW_PER_PAGE);

        long count = publisherService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("publishers", publisherList);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "publisher/publisherList";
    }

    @GetMapping(value = "/{publisherId}")
    public String getPublisherById(Model model, @PathVariable long publisherId){
        Publisher publisher = null;
        try {
            publisher = publisherService.findById(publisherId);
            model.addAttribute("allowDelete",false);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find publisher" + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("publisher",publisher);
        return "publisher/publisher";
    }

    @GetMapping(value = {"/add"})
    public String showAddPublisher(Model model) {
        Publisher publisher = new Publisher();
        model.addAttribute("add",true);
        model.addAttribute("publisher",publisher);
        return "publisher/publisher-add";
    }

    @PostMapping(value = {"/add"})
    public String addPublisher(Model model, @ModelAttribute("publisher") Publisher publisher) {
        return null;
    }

    @GetMapping(value = {"/{publisherId}/edit"})
    public String showEditPublisher(Model model, @PathVariable long publisherId) {
        Publisher publisher = null;
        try {
            publisher = publisherService.findById(publisherId);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find publisher: "+ e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("add",false);
        model.addAttribute("publisher",publisher);
        return "publisher/publisher-edit";
    }

    @PostMapping(value = {"/{publisherId}/edit"})
    public String editPublisher(Model model, @PathVariable long publisherId, @ModelAttribute("publisher") Publisher publisher) {
        try {
            Publisher oldPublisher = publisherService.findById(publisherId);
            oldPublisher.setTitle(publisher.getTitle());
            publisherService.update(oldPublisher);
            return "redirect:/publishers/" + String.valueOf(oldPublisher.getId());
        } catch (Exception e) {
            logger.log(Level.ERROR,"cannot update publisher: "+ e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("add", false);
            return "publisher/publisher-edit";
        }
    }

    @GetMapping(value = {"/{publisherId}/delete"})
    public String showDeletePublisher(Model model, @PathVariable long publisherId) {
        Publisher publisher = null;
        try {
            publisher = publisherService.findById(publisherId);
            model.addAttribute("allowDelete",true);
        } catch (Exception e){
            logger.log(Level.ERROR,"cannot find publisher" + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("publisher",publisher);
        return "publisher/publisher" +
                "";
    }

    @PostMapping(value = {"/{publisherId}/delete"})
    public String deletePublisher(Model model, @PathVariable long publisherId, @ModelAttribute("publisher") Publisher publisher) {
        try {
            publisherService.deleteById(publisherId);
            return "redirect:publishers/";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.log(Level.ERROR,"cannot delete publisher: "+ ex.getMessage());
            model.addAttribute("errorMessage", errorMessage);

            return "publisher/publisher";
        }
    }
}
