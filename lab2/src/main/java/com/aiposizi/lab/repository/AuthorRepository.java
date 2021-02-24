package com.aiposizi.lab.repository;

import com.aiposizi.lab.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

       List<Author> findByFirstnameAndLastname(String firstname, String lastname);
}
