package com.luke.springdatajpademo.repository;

import com.luke.springdatajpademo.dto.BookNameAndDesc;
import com.luke.springdatajpademo.entity.BookEntity;
import org.junit.jupiter.api.BeforeEach;
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
@DataJpaTest
class BookRepositoryTests {

    public static final String TLOR_NAME = "The Lord of the Rings";
    public static final String TLOR_DESC = "masterpiece";
    public static final String PUBLISHER = "Luke";
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(TLOR_NAME);
        bookEntity.setDescription(TLOR_DESC);
        bookEntity.setPublisher(PUBLISHER);
        bookEntity.setYear(2024);
        bookEntity.setAuthorId(1L);

        entityManager.persist(bookEntity);
    }
    
    @Test
    void testFindById() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("Harry Potter");
        bookEntity.setDescription("magic");
        bookEntity.setPublisher(PUBLISHER);
        bookEntity.setAuthorId(2L);

        BookEntity harryPotter = entityManager.persist(bookEntity);

        Optional<BookEntity> bookEntityById = bookRepository.findById(harryPotter.getId());
        assertTrue(bookEntityById.isPresent());
    }

    @Test
    void testFindByName() {
        BookEntity theLordOfTheRings = bookRepository.findByName(TLOR_NAME);
        assertNotNull(theLordOfTheRings);
    }

    @Test
    void testPageRequestWithList() {
        PageRequest pageRequestByPublisher = PageRequest.of(0, 2, Sort.by("name").descending());
        List<BookEntity> allByPublisher = bookRepository.findAllByPublisher(PUBLISHER, pageRequestByPublisher);
        assertFalse(allByPublisher.isEmpty());
    }

    @Test
    void testPageRequestWithPage() {
        PageRequest pageRequestByName = PageRequest.of(0, 2, Sort.by("description").descending());
        Page<BookEntity> allByName = bookRepository.findAllByName(TLOR_NAME, pageRequestByName);
        assertFalse(allByName.isEmpty());
    }

    @Test
    void testPageRequestWithSlice() {
        PageRequest pageRequestByDescription = PageRequest.of(0, 2, Sort.by("publisher").descending());
        Slice<BookEntity> allByDescription = bookRepository.findAllByDescription(TLOR_DESC, pageRequestByDescription);
        assertFalse(allByDescription.isEmpty());
    }

    @Test
    void testNamedQuery1() {
        BookEntity namedQueryResult = bookRepository.namedQuery1(TLOR_NAME, TLOR_DESC);
        assertNotNull(namedQueryResult);
    }

    @Test
    void testNamedQuery2() {
        BookEntity namedQueryResult = bookRepository.namedQuery2(TLOR_NAME, 2024);
        assertNotNull(namedQueryResult);
    }

    @Test
    void testNamedNativeQuery() {
        BookEntity namedNativeQueryResult = bookRepository.namedNativeQuery(TLOR_NAME, TLOR_DESC);
        assertNotNull(namedNativeQueryResult);
    }

    @Test
    void testJpqlQueryWithDtoProjection() {
        BookNameAndDesc jpqlQueryWithDtoProjectionResult = bookRepository.jpqlQueryWithDtoProjection(TLOR_NAME);
        assertNotNull(jpqlQueryWithDtoProjectionResult);
        assertNotNull(jpqlQueryWithDtoProjectionResult.getName());
        assertNotNull(jpqlQueryWithDtoProjectionResult.getDescription());
    }

    @Test
    void testNamedNativeQueryWithDtoProjection() {
        BookNameAndDesc namedNativeQueryWithDtoProjectionResult = bookRepository.namedNativeQueryWithDtoProjection(TLOR_NAME);
        assertNotNull(namedNativeQueryWithDtoProjectionResult);
        assertNotNull(namedNativeQueryWithDtoProjectionResult.getName());
        assertNotNull(namedNativeQueryWithDtoProjectionResult.getDescription());
    }

}