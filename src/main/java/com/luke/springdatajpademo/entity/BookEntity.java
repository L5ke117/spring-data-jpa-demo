package com.luke.springdatajpademo.entity;

import com.luke.springdatajpademo.dto.BookNameAndDesc;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@NamedQuery(name = "BookEntity.findByNameAndDescription", query = "SELECT b from BookEntity b WHERE b.name = ?1 and b.description = ?2")
@NamedQuery(name = "BookEntity.namedQuery2", query = "SELECT b from BookEntity b WHERE b.name = ?1 and b.year = ?2")
@NamedNativeQuery(name = "BookEntity.namedNativeQuery", query = "SELECT * from book WHERE name = ? and description = ?", resultClass = BookEntity.class)
@NamedNativeQuery(name = "BookEntity.namedNativeQueryWithDtoProjection", query = "SELECT name, description from book WHERE name = ?", resultSetMapping = "BookNameDescMapping")
@SqlResultSetMapping(
        name = "BookNameDescMapping",
        classes = @ConstructorResult(
                targetClass = BookNameAndDesc.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "description", type = String.class)
                }
        ))
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
