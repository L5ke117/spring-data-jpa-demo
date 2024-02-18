package com.luke.springdatajpademo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "store_product",
            joinColumns = { @JoinColumn(name = "store_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id") })
    private Set<ProductEntity> products = new HashSet<>();

    public void addProduct(ProductEntity product) {
        products.add(product);
        product.getStores().add(this);
    }

    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.getStores().remove(this);
    }

}
