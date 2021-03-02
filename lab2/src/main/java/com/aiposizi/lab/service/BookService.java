package com.aiposizi.lab.service;

import com.aiposizi.lab.dto.AuthorDto;
import com.aiposizi.lab.dto.BookDto;
import com.aiposizi.lab.dto.PublisherDto;
import com.aiposizi.lab.entity.Author;
import com.aiposizi.lab.entity.Book;
import com.aiposizi.lab.entity.Publisher;
import com.aiposizi.lab.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    PublisherService publisherService;

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
    public List<Book> findAll(){
        return bookRepository.findAll();
    }
    public Book create(BookDto book) throws Exception {

        if(book.getTitle().isEmpty()){
            throw new Exception("title is required");
        }

        Publisher publisher;
        Author author;

        if(book.getPublisher().isEmpty()||book.getPublisher()==null) {
            throw new ServiceException("publishing title is empty");
        }
        List<Publisher> publishers = publisherService.findByTitle(book.getPublisher());
        if(publishers.isEmpty())
        {
            throw new ServiceException("can't find publisher with this title: "+book.getPublisher());
        }else publisher = publishers.get(0);

        String[] authorName = book.getAuthor().split(" ");
        try {
            List<Author> authors = authorService.findByFirstnameAndLastname(authorName[0],authorName[1]);
            if(authors.isEmpty())
            {
                throw new ServiceException("can't find author with this name: "+book.getAuthor());
            }
            else author = authors.get(0);
        }catch (IndexOutOfBoundsException e){
            throw new ServiceException("name of author must contains 2 words");
        }

        Book newBook = new Book();

        newBook.setYear(book.getYear());
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(author);
        newBook.setPublisher(publisher);

        Book saved = bookRepository.save(newBook);
        publisher.getPublications().add(saved);
        author.getBooks().add(saved);
        authorService.update(author);
        publisherService.update(publisher);
        return saved;
    }


    public Book update(Book book) throws Exception {
        if(!existsById(book.getId())){
            throw new ServiceException("Cannot find book with id " + book.getId());
        }
        if(book.getTitle().isEmpty()){
            throw new ServiceException("title is required");
        }
        return bookRepository.save(book);
    }

    public void deleteById(Long id) throws Exception {
        if(!existsById(id)){
            throw new ServiceException("Cannot find book with id " + id);
        }
        bookRepository.deleteById(id);
    }

    public Long count(){
        return bookRepository.count();
    }

    public Optional<Book> findByTitle(String title){
        return bookRepository.findByTitle(title);
    }
}
