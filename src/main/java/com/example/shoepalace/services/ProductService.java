package com.example.shoepalace.services;

import com.example.shoepalace.model.Product;
import com.example.shoepalace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.getAll();
    }

    public Product addproduct(Product p){
        return productRepository.createProduct(p);
    }

    public Optional<Product> getById(String id){
        return productRepository.findProductById(id);
    }


}
