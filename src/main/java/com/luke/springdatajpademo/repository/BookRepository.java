package com.luke.springdatajpademo.repository;

import com.luke.springdatajpademo.dto.BookNameAndDesc;
import com.luke.springdatajpademo.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    // -------------------------------------------- Query Methods ---------------------------------------------

    BookEntity findByName(String name);

    List<BookEntity> findByNameLike(String name);

    List<BookEntity> findFirst5ByNameContainsOrderByDescriptionDesc(String name);

    // -------------------------------------------- Pagination ---------------------------------------------

    List<BookEntity>  findAllByPublisher(String publisher, Pageable pageable);

    Slice<BookEntity> findAllByDescription(String description, Pageable pageable);

    // Page requires an extra query to get total count
    Page<BookEntity> findAllByName(String name, Pageable pageable);


    // -------------------------------------------- Named Queries ---------------------------------------------

    @Query(name = "BookEntity.findByNameAndDescription")
    BookEntity namedQuery1(String name, String description);

    @Query(name = "BookEntity.findByNameAndDescription")
    List<BookEntity> namedQuery1WithListResult(String name, String description);

    BookEntity namedQuery2(String name, Integer year);

    @Query(name = "BookEntity.namedNativeQuery", nativeQuery = true)
    BookEntity namedNativeQuery(String name, String description);

    // -------------------------------------------- Custom Queries ---------------------------------------------

    @Query(value = "SELECT b FROM BookEntity b")
    List<BookEntity> repoJpqlQuery1();

    @Query(value = "SELECT b FROM BookEntity b")
    List<BookEntity> repoJpqlQuery2(Pageable pageable);

    @Query(value = "SELECT * FROM book WHERE name = ?", nativeQuery = true)
    List<BookEntity> repoNativeQuery1(String name);

    @Query(value = "SELECT * FROM book WHERE name = ?", nativeQuery = true)
    List<BookEntity> repoNativeQuery2(String name, Pageable pageable);

    @Query(value = "UPDATE BookEntity SET name = :prefix || name WHERE publisher= :publisher")
    @Modifying
    void customUpdate(String prefix, String publisher);

    @Query(value = "DELETE FROM BookEntity b WHERE b.name = :name")
    @Modifying
    void customDelete(String name);

    // -------------------------------------------- DTO Projection ---------------------------------------------

    @Query(value = "SELECT new com.luke.springdatajpademo.dto.BookNameAndDesc(b.name, b.description) FROM BookEntity b where b.name = ?1")
    BookNameAndDesc jpqlQueryWithDtoProjection(String name);

    @Query(name = "BookEntity.namedNativeQueryWithDtoProjection", nativeQuery = true)
    BookNameAndDesc namedNativeQueryWithDtoProjection(String name);

}
