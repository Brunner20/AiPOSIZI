package com.aiposizi.lab.repository;



import com.aiposizi.lab.entity.Publisher;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PublisherRepository extends PagingAndSortingRepository<Publisher,Long> {

    List<Publisher> findByTitle(String title);
}
