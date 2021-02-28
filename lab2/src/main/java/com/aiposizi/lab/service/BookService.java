package com.aiposizi.lab.service;

import com.aiposizi.lab.dto.AuthorDto;
import com.aiposizi.lab.dto.BookDto;
import com.aiposizi.lab.dto.PublisherDto;
import com.aiposizi.lab.entity.Author;
import com.aiposizi.lab.entity.Book;
import com.aiposizi.lab.entity.Publisher;
import com.aiposizi.lab.repository.AuthorRepository;
import com.aiposizi.lab.repository.BookRepository;
import com.aiposizi.lab.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorRepository;

    @Autowired
    PublisherService publisherRepository;

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
    public Book create(BookDto book) throws Exception {

        if(book.getTitle().isEmpty()){
            throw new Exception("title is required");
        }

        Publisher publisher;
        Author author;


        if(book.getPublisher().isEmpty()||book.getPublisher()==null) {
            throw new ServiceException("publishing title is empty");
        }
        List<Publisher> publishers = publisherRepository.findByTitle(book.getTitle());
        if(publishers.isEmpty())
        {
            publisher = new Publisher();
            publisher.setTitle(book.getPublisher());
        }else publisher = publishers.get(0);


        String[] authorName = book.getAuthor().split(" ");
        try {
            List<Author> authors = authorRepository.findByFirstnameAndLastname(authorName[0],authorName[1]);
            if(authors.isEmpty())
            {
                author = new Author();
                author.setFirstname(authorName[0]);
                author.setLastname(authorName[1]);
                //
                author.setYear(new Date(1234));
                //
            }
            else author = authors.get(0);
        }catch (IndexOutOfBoundsException e){
            throw new ServiceException("name of author must contains 2 words");
        }


        authorRepository.save(new AuthorDto(author));
        publisherRepository.save(new PublisherDto(publisher));

        return save(book,author,publisher);
    }

    public Book save(BookDto book,Author author,Publisher publisher){
        Book newBook = new Book();
        newBook.setYear(book.getYear());
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(author);
        newBook.setPublisher(publisher);

        publisher.getPublications().add(newBook);
        author.getBooks().add(newBook);
        return bookRepository.save(newBook);
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

    public Long count(){
        return bookRepository.count();
    }


}
