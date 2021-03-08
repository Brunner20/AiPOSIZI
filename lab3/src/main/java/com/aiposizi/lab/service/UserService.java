package com.aiposizi.lab.service;

import com.aiposizi.lab.dto.UserDto;
import com.aiposizi.lab.entity.User;
import com.aiposizi.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll(int page, int rowPerPage){
        List<User> users = new ArrayList<>();
        Pageable sortedPage = PageRequest.of(page-1,rowPerPage, Sort.by("id").ascending());
        userRepository.findAll(sortedPage).forEach(users::add);
        return users;
    }

    public User save(UserDto user) throws Exception {

        if(user.getFirstname().isEmpty() || user.getLastname().isEmpty()){
            throw new ServiceException("Firstname and Lastname are required");
        }
        User newUser = new User();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        return userRepository.save(newUser);
    }

    public User update(User user) throws Exception {
        if(!existsById(user.getId())){
            throw new ServiceException("Cannot find user with id " + user.getId());
        }
        if(user.getFirstname().isEmpty() || user.getLastname().isEmpty()){
            throw new ServiceException("Firstname and Lastname are required");
        }
        return userRepository.save(user);
    }

    public void deleteById(Long id) throws Exception {
        if(!existsById(id)){
            throw new ServiceException("Cannot find User with id " + id);
        }
        userRepository.deleteById(id);
    }

    public Long count(){
        return userRepository.count();
    }
}
