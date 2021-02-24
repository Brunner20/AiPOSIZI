package com.aiposizi.lab.service;

import com.aiposizi.lab.dto.BookDto;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    PublisherRepository publisherRepository;

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

    public Long count(){
        return bookRepository.count();
    }

    public Book dtoToBook(BookDto bookDto) throws Exception {
        Book book =new Book();
        String[] authorName = bookDto.getAuthor().split(" ");

        Author author;
        Publisher publisher;

        try {
            List<Author> authors = authorService.findByFirstnameAndLastname(authorName[0],authorName[1]);
            if(authors.isEmpty())
            {
                author = new Author();
                author.setFirstname(authorName[0]);
                author.setLastname(authorName[1]);
            }
            else author = authors.get(0);
        }catch (IndexOutOfBoundsException e){
            throw new ServiceException("name of author must contains 2 words");
        }
        author.getBooks().add(book);
        authorService.save(author);

        if(bookDto.getPublisher().isEmpty()||bookDto.getPublisher()==null) {
            throw new ServiceException("publishing title is empty");
        }
        List<Publisher> publishers = publisherRepository.findByTitle(bookDto.getTitle());
        if(publishers.isEmpty())
        {
            publisher = new Publisher();
            publisher.setTitle(bookDto.getPublisher());
        }else publisher = publishers.get(0);
        publisher.getPublications().add(book);

        book.setYear(bookDto.getYear());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        return book;
    }
}
