package com.aiposizi.lab.dto;

import com.aiposizi.lab.entity.Publisher;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PublisherDto {

    private Long id;
    @NotEmpty
    private String title;

    public PublisherDto(Publisher publisher) {
        this.id = publisher.getId();
        this.title = publisher.getTitle();
    }
}
