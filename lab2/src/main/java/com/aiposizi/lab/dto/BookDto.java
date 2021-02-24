package com.aiposizi.lab.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class BookDto {

    @NotNull
    private String title;

    @NotNull
    private String publisher;

    @NotNull
    private String author;

    @NotNull
    private Date year;
}
