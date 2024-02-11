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
        BookEntity bookEntity1 = new BookEntity();
        bookEntity1.setName(TLOR_NAME);
        bookEntity1.setDescription(TLOR_DESC);
        bookEntity1.setPublisher(PUBLISHER);
        bookEntity1.setYear(2024);
        bookEntity1.setAuthorId(1L);

        entityManager.persist(bookEntity1);
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
        insertDataForPagingTests();
        PageRequest pageRequestByPublisher = PageRequest.of(1, 1, Sort.by("name").descending());
        List<BookEntity> allByPublisher = bookRepository.findAllByPublisher(PUBLISHER, pageRequestByPublisher);
        assertFalse(allByPublisher.isEmpty());
    }

    @Test
    void testPageRequestWithSlice() {
        insertDataForPagingTests();
        PageRequest pageRequestByDescription = PageRequest.of(1, 1, Sort.by("publisher").descending());
        Slice<BookEntity> sliceByDescription = bookRepository.findAllByDescription("desc", pageRequestByDescription);
        assertFalse(sliceByDescription.isEmpty());
        assertFalse(sliceByDescription.isFirst());
        assertFalse(sliceByDescription.isLast());
        assertTrue(sliceByDescription.hasPrevious());
        assertTrue(sliceByDescription.hasNext());
    }

    @Test
    void testPageRequestWithPage() {
        insertDataForPagingTests();
        PageRequest pageRequestByName = PageRequest.of(0, 1, Sort.by("description").descending());
        Page<BookEntity> pageByName = bookRepository.findAllByName("name", pageRequestByName);
        assertFalse(pageByName.isEmpty());
        assertEquals(3, pageByName.getTotalPages());
        PageRequest pageRequestByName2 = PageRequest.of(1, 1);
        Page<BookEntity> pageByName2 = bookRepository.findAllByName("name", pageRequestByName2);
        assertFalse(pageByName2.isEmpty());
        assertEquals(3, pageByName2.getTotalPages());
    }

    @Test
    void testNamedQuery1() {
        BookEntity namedQueryResult = bookRepository.namedQuery1(TLOR_NAME, TLOR_DESC);
        assertNotNull(namedQueryResult);
    }

    @Test
    void testNamedQuery1WithListResult() {
        List<BookEntity> namedQuery1WithListResult = bookRepository.namedQuery1WithListResult(TLOR_NAME, TLOR_DESC);
        assertFalse(namedQuery1WithListResult.isEmpty());
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
    void testRepoJpqlQuery1() {
        List<BookEntity> repoJpqlQueryListResult = bookRepository.repoJpqlQuery1();
        assertFalse(repoJpqlQueryListResult.isEmpty());
    }

    @Test
    void testRepoNativeQuery1() {
        List<BookEntity> repoNativeQueryListResult = bookRepository.repoNativeQuery1(TLOR_NAME);
        assertFalse(repoNativeQueryListResult.isEmpty());
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

    private void insertDataForPagingTests() {
        BookEntity bookEntity2 = BookEntity.builder().name("name").description("desc").year(2024).publisher(PUBLISHER).authorId(1L).build();
        entityManager.persist(bookEntity2);
        BookEntity bookEntity3 = BookEntity.builder().name("name").description("desc").year(2024).publisher(PUBLISHER).authorId(1L).build();
        entityManager.persist(bookEntity3);
        BookEntity bookEntity4 = BookEntity.builder().name("name").description("desc").year(2024).publisher(PUBLISHER).authorId(1L).build();
        entityManager.persist(bookEntity4);
    }

}