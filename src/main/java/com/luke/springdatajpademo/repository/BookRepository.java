package com.luke.springdatajpademo.repository;

import com.luke.springdatajpademo.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    BookEntity findByName(String name);

    List<BookEntity>  findAllByPublisher(String publisher, Pageable pageable);

    Page<BookEntity> findAllByName(String name, Pageable pageable);

    Slice<BookEntity> findAllByDescription(String description, Pageable pageable);
}
