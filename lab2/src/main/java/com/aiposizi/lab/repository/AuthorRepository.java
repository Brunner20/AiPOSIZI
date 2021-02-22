package com.aiposizi.lab.repository;

import com.aiposizi.lab.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
}
