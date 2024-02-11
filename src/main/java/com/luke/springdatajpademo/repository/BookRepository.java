package com.luke.springdatajpademo.repository;

import com.luke.springdatajpademo.dto.BookNameAndDesc;
import com.luke.springdatajpademo.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    BookEntity findByName(String name);

    List<BookEntity>  findAllByPublisher(String publisher, Pageable pageable);

    Page<BookEntity> findAllByName(String name, Pageable pageable);

    Slice<BookEntity> findAllByDescription(String description, Pageable pageable);

    @Query(name = "BookEntity.findByNameAndDescription")
    BookEntity namedQuery1(String name, String description);

    @Query(name = "BookEntity.findByNameAndDescription")
    List<BookEntity> namedQuery1WithListResult(String name, String description);

    BookEntity namedQuery2(String name, Integer year);

    @Query(name = "BookEntity.namedNativeQuery", nativeQuery = true)
    BookEntity namedNativeQuery(String name, String description);

    @Query(value = "SELECT b FROM BookEntity b")
    List<BookEntity> repoJpqlQuery1();

    @Query(value = "SELECT * FROM book WHERE name = ?", nativeQuery = true)
    List<BookEntity> repoNativeQuery1(String name);

    @Query(value = "SELECT new com.luke.springdatajpademo.dto.BookNameAndDesc(b.name, b.description) FROM BookEntity b where b.name = ?1")
    BookNameAndDesc jpqlQueryWithDtoProjection(String name);

    @Query(name = "BookEntity.namedNativeQueryWithDtoProjection", nativeQuery = true)
    BookNameAndDesc namedNativeQueryWithDtoProjection(String name);

}
