package com.aiposizi.lab.entity;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@Getter @Setter @EqualsAndHashCode
@ToString
public class Book implements Serializable {

    private static final long serialVersionUID = 63453822723859663L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id",updatable = false,nullable = false)
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "writing_year")
    private Date year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher")
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> owners = new ArrayList<>();
}
