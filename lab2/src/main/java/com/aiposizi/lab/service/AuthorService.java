package com.aiposizi.lab.service;

import com.aiposizi.lab.entity.Author;
import com.aiposizi.lab.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public boolean existsById(Long id){
        return authorRepository.existsById(id);
    }

    public Author findById(Long id){
        return authorRepository.findById(id).orElse(null);
    }

    public List<Author> findAll(int page, int rowPerPage){
        List<Author> authors = new ArrayList<>();
        Pageable sortedPage = PageRequest.of(page-1,rowPerPage, Sort.by("id").ascending());
        authorRepository.findAll(sortedPage).forEach(authors::add);
        return authors;
    }
    public Author save(Author author) throws Exception {
        if(author.getLastname().isEmpty()||author.getFirstname().isEmpty()){
            throw new Exception("Name is required");
        }
        return authorRepository.save(author);
    }

    public Author update(Author author) throws Exception {
        if(!existsById(author.getId())){
            throw new Exception("Cannot find author with id " + author.getId());
        }
        if(author.getLastname().isEmpty()||author.getFirstname().isEmpty()){
            throw new Exception("Name is required");
        }
        return authorRepository.save(author);
    }

    public void deleteById(Long id) throws Exception {
        if(!existsById(id)){
            throw new Exception("Cannot find author with id " + id);
        }
        authorRepository.deleteById(id);
    }

    public List<Author> findByFirstnameAndLastname(String s, String s1) {
       return authorRepository.findByFirstnameAndLastname(s,s1);
    }

    public Long count(){
        return authorRepository.count();
    }
}
