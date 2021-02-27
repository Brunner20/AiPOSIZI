package com.aiposizi.lab.entity;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @EqualsAndHashCode
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 6345382272309663L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "firstname")
    private String firstname;


    @Column(name = "lastname")
    private String lastname;

    @ManyToMany
    @JoinTable(
            name = "m2m_books_user",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    private List<Book> books = new ArrayList<>();
}
