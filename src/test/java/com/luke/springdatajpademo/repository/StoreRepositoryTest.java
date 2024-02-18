package com.luke.springdatajpademo.repository;

import com.luke.springdatajpademo.entity.ProductEntity;
import com.luke.springdatajpademo.entity.StoreEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class StoreRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        StoreEntity store = StoreEntity.builder().name("store1").address("address1").build();
        ProductEntity product1 = ProductEntity.builder().name("product1").category("category1").manufacturer("manufacturer1").build();
        ProductEntity product2 = ProductEntity.builder().name("product2").category("category2").manufacturer("manufacturer2").build();
        store.addProduct(product1);
        store.addProduct(product2);
        storeRepository.save(store);
        // very important!!! this is to force entity manager to retrieve the association mappings in the following test cases
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testFindStoreByName() {
        StoreEntity store1 = storeRepository.findByName("store1");
        assertNotNull(store1);
        assertEquals(2, store1.getProducts().size());
    }

    @Test
    void testFindStoreByProductName() {
        StoreEntity store = storeRepository.findByProductName("product1");
        assertNotNull(store);
        assertEquals(2, store.getProducts().size());
    }

    @Test
    void testFindProductsByStoreName() {
        List<ProductEntity> productsByStoreName = storeRepository.findProductsByStoreName("store1");
        assertEquals(2, productsByStoreName.size());
    }
}