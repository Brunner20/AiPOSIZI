package com.aiposizi.lab.repository;

import com.aiposizi.lab.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
