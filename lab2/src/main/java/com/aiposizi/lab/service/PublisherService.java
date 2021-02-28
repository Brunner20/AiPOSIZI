package com.aiposizi.lab.service;

import com.aiposizi.lab.dto.PublisherDto;
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
    public Publisher save(PublisherDto publisher) throws Exception {

        if(publisher.getTitle().isEmpty()){
            throw new ServiceException("title is required");
        }
        Publisher newPublisher = new Publisher();
        newPublisher.setTitle(publisher.getTitle());
        return publisherRepository.save(newPublisher);
    }

    public Publisher update(Publisher publisher) throws Exception {
        if(!existsById(publisher.getId())){
            throw new ServiceException("Cannot find publisher with id " + publisher.getId());
        }
        if(publisher.getTitle().isEmpty()){
            throw new ServiceException("title is required");
        }
        return publisherRepository.save(publisher);
    }

    public void deleteById(Long id) throws Exception {
        if(!existsById(id)){
            throw new ServiceException("Cannot find publisher with id " + id);
        }
        publisherRepository.deleteById(id);
    }
    public Long count(){return publisherRepository.count();}


    public List<Publisher> findByTitle(String title) {
        return publisherRepository.findByTitle(title);
    }
}
