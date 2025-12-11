package com.example.shoepalace.controller;

import com.example.shoepalace.model.Product;
import com.example.shoepalace.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    // spring will inject productService when productController bean is created
    private final ProductService productService;

    public ProductController(final ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("products", products);
        return ResponseEntity.ok(body); // status 200
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addNewProduct(@RequestBody Product product){
        Product addedProduct = productService.addproduct(product);

        System.out.println(addedProduct);
        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("product", addedProduct);
        return ResponseEntity.ok(body); // status 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findSingleProduct(@PathVariable String id) {
        Optional<Product> product = productService.getById(id);

        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "success", false,
                            "message", "No product found with the given id."
                    )
            );
        }

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "product", product.get()
                )
        );
    }

//    public ResponseEntity<Map<String,Object>> findSingleProduct(@PathVariable Long id){
//        return productService.getById(id).map(
//                p -> {
//                    Map<String, Object> body = new HashMap<>();
//                    body.put("success", true);
//                    body.put("product", List.of(p)); // Express returns [product]
//                    return ResponseEntity.ok(body);
//                }
//        ).orElseGet(() -> {
//            Map<String, Object> error = new HashMap<>();
//            error.put("success", false);
//            error.put("message",
//                    "The product ID sent has no product associated with it. Check and try again");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//        }
//        );
//
//    }

}
