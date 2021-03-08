package com.aiposizi.lab.repository;

import com.aiposizi.lab.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book,Long> {

    List<Book> findAll();

    Optional<Book> findByTitle(String title);
}
