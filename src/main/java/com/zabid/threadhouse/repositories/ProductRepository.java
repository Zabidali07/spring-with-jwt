package com.zabid.threadhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zabid.threadhouse.models.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
