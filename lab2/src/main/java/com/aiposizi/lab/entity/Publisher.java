package com.aiposizi.lab.entity;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
@Getter @Setter @EqualsAndHashCode
@ToString(exclude = "publications")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long id;

    @NotNull
    @Column(name = "p_title")
    private String title;

    @OneToMany(mappedBy = "publisher")
    private List<Book> publications = new ArrayList<>();


}
