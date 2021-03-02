package com.aiposizi.lab.dto;

import com.aiposizi.lab.entity.Author;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Setter
@Getter
public class AuthorDto {


    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    private Date year;

    public AuthorDto(Author author) {
        this.firstname = author.getFirstname();
        this.lastname = author.getLastname();
        this.year = author.getYear();
    }

    public AuthorDto() {
    }
}
