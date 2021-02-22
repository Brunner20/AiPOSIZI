package com.aiposizi.lab.service;

import com.aiposizi.lab.entity.Book;
import com.aiposizi.lab.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public boolean existsById(Long id){
        return bookRepository.existsById(id);
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAll(int page, int rowPerPage){
        List<Book> books = new ArrayList<>();
        Pageable sortedPage = PageRequest.of(page-1,rowPerPage, Sort.by("id").ascending());
        bookRepository.findAll(sortedPage).forEach(books::add);
        return books;
    }
    public Book save(Book book) throws Exception {
        if(existsById(book.getId())&&book.getId()!=null){
            throw new Exception("Book with id: " + book.getId() + " already exists");
        }
        if(book.getTitle().isEmpty()){
            throw new Exception("title is required");
        }
        return bookRepository.save(book);
    }

    public Book update(Book book) throws Exception {
        if(!existsById(book.getId())){
            throw new Exception("Cannot find book with id " + book.getId());
        }
        if(book.getTitle().isEmpty()){
            throw new Exception("title is required");
        }
        return bookRepository.save(book);
    }

    public void deleteById(Long id) throws Exception {
        if(!existsById(id)){
            throw new Exception("Cannot find book with id " + id);
        }
        bookRepository.deleteById(id);
    }
}
