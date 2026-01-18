package com.example.shoepalace.services;

import com.example.shoepalace.model.Product;
import com.example.shoepalace.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // CREATE PRODUCT
    public Product addProduct(Product product){
        return productRepository.createProduct(product);
    }

    // GET PRODUCT BY ID
    public Optional<Product> getById(String id){
        return productRepository.findProductById(id);
    }

    // UPDATE
    public Product updateProduct(Product product){
        return productRepository.updateProduct(product);
    }

    // DELETE
    public boolean deleteProduct(String id){
        return productRepository.deleteProduct(id);
    }
}
