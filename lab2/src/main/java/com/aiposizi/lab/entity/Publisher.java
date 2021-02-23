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
public class Publisher {

    @Id
    @Column(name = "p_id")
    private Long id;

    @NotNull
    @Column(name = "p_title")
    private String title;

    @OneToMany(mappedBy = "publisher")
    private List<Book> publications = new ArrayList<>();

    @Override
    public String toString() {
        return title;
    }
}
