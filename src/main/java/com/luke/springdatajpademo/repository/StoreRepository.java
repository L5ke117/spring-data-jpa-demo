package com.luke.springdatajpademo.repository;

import com.luke.springdatajpademo.entity.ProductEntity;
import com.luke.springdatajpademo.entity.StoreEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends CrudRepository<StoreEntity, Long> {

    StoreEntity findByName(String name);

    @Query(value = "SELECT s FROM StoreEntity s JOIN s.products p ON p.name = ?1")
    StoreEntity findByProductName(String productName);

    @Query(value = "SELECT s.products FROM StoreEntity s WHERE s.name = ?1")
    List<ProductEntity> findProductsByStoreName(String storeName);
}
