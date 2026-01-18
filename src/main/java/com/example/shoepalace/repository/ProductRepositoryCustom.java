package com.example.shoepalace.repository;

import com.example.shoepalace.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    List<Product> getAll();

    Product createProduct(Product p);

    Optional<Product> findProductById(String id);

    Product updateProduct(Product product);

    boolean deleteProduct(String id);
}
