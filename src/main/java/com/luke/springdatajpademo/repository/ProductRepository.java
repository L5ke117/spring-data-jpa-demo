package com.luke.springdatajpademo.repository;

import com.luke.springdatajpademo.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    ProductEntity findByName(String name);
}
