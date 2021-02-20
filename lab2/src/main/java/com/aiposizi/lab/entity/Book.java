package com.aiposizi.lab.entity;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.io.Serializable;
import java.util.GregorianCalendar;

@Entity
@Table(name = "books")
@Getter @Setter @EqualsAndHashCode
@ToString
public class Book implements Serializable {

    private static final long serialVersionUID = 63453822723859663L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "writing_year")
    private GregorianCalendar year;

    @Column(name = "publisher")
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    @Column(name = "author")
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

}
