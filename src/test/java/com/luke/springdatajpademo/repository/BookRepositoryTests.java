package com.luke.springdatajpademo.repository;

import com.luke.springdatajpademo.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@AutoConfigureTestDatabase
@DataJpaTest(showSql = true)
class BookRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void test() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("The Lord of the Rings");
        bookEntity.setDescription("masterpiece");
        bookEntity.setPublisher("Luke");
        bookEntity.setAuthorId(1L);

        BookEntity persistedBook = entityManager.persist(bookEntity);

        Optional<BookEntity> bookEntityById = bookRepository.findById(persistedBook.getId());
        assertTrue(bookEntityById.isPresent());

        BookEntity theLordOfTheRings = bookRepository.findByName("The Lord of the Rings");
        assertNotNull(theLordOfTheRings);

        PageRequest pageRequestByPublisher = PageRequest.of(0, 2, Sort.by("name").descending());
        List<BookEntity> allByPublisher = bookRepository.findAllByPublisher("Luke", pageRequestByPublisher);
        assertFalse(allByPublisher.isEmpty());

        PageRequest pageRequestByName = PageRequest.of(0, 2, Sort.by("description").descending());
        Page<BookEntity> allByName = bookRepository.findAllByName("The Lord of the Rings", pageRequestByName);
        assertFalse(allByName.isEmpty());

        PageRequest pageRequestByDescription = PageRequest.of(0, 2, Sort.by("publisher").descending());
        Slice<BookEntity> allByDescription = bookRepository.findAllByDescription("masterpiece", pageRequestByDescription);
        assertFalse(allByDescription.isEmpty());

    }

}