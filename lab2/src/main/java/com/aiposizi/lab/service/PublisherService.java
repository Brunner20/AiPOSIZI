package com.aiposizi.lab.service;

import com.aiposizi.lab.entity.Publisher;
import com.aiposizi.lab.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {

    @Autowired
    PublisherRepository publisherRepository;

    public boolean existsById(Long id){
        return publisherRepository.existsById(id);
    }

    public Publisher findById(Long id){
        return publisherRepository.findById(id).orElse(null);
    }

    public List<Publisher> findAll(int page, int rowPerPage){
        List<Publisher> publishers = new ArrayList<>();
        Pageable sortedPage = PageRequest.of(page-1,rowPerPage, Sort.by("id").ascending());
        publisherRepository.findAll(sortedPage).forEach(publishers::add);
        return publishers;
    }
    public Publisher save(Publisher publisher) throws Exception {
        if(existsById(publisher.getId())&&publisher.getId()!=null){
            throw new Exception("Publisher with id: " + publisher.getId() + " already exists");
        }
        if(publisher.getTitle().isEmpty()){
            throw new Exception("title is required");
        }
        return publisherRepository.save(publisher);
    }

    public Publisher update(Publisher publisher) throws Exception {
        if(!existsById(publisher.getId())){
            throw new Exception("Cannot find publisher with id " + publisher.getId());
        }
        if(publisher.getTitle().isEmpty()){
            throw new Exception("title is required");
        }
        return publisherRepository.save(publisher);
    }

    public void deleteById(Long id) throws Exception {
        if(!existsById(id)){
            throw new Exception("Cannot find publisher with id " + id);
        }
        publisherRepository.deleteById(id);
    }
}
