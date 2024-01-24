package com.luke.springdatajpademo.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "publication_year")
    private Integer year;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "author_id")
    private Long authorId;
}
