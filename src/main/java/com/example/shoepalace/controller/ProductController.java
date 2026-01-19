package com.example.shoepalace.controller;

import com.example.shoepalace.mapper.ProductFilterMetadataMapper;
import com.example.shoepalace.model.Product;
import com.example.shoepalace.responseDTO.PageResponseDTO;
import com.example.shoepalace.responseDTO.ProductFilterMetadataDTO;
import com.example.shoepalace.responseDTO.SingleProductDetailResponseDTO;
import com.example.shoepalace.services.ProductFilterMetadataService;
import com.example.shoepalace.services.ProductFilterService;
import com.example.shoepalace.services.ProductService;
import com.example.shoepalace.services.SingleProductDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    // spring will inject productService when productController bean is created
    private final ProductService productService;
    private final ProductFilterService productFilterService;
    private final ProductFilterMetadataService metadataService;
    private final ProductFilterMetadataMapper metadataMapper;
    private final SingleProductDetailService singleProductDetailService;


    public ProductController(ProductService productService,
                             ProductFilterService productFilterService,
                             ProductFilterMetadataService metadataService,
                             ProductFilterMetadataMapper metadataMapper,
                             SingleProductDetailService singleProductDetailService){
        this.productService = productService;
        this.productFilterService = productFilterService;
        this.metadataService = metadataService;
        this.metadataMapper = metadataMapper;
        this.singleProductDetailService = singleProductDetailService;
    }


    @GetMapping
    public ResponseEntity<PageResponseDTO<Product>> getProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "newest") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int sizePage,
            @RequestParam(required = false) String search
    ) {
        PageResponseDTO<Product> response = productFilterService.getProducts(
                brand,
                size,
                color,
                categories,
                gender,
                minPrice,
                maxPrice,
                sort,
                page,
                sizePage,
                search
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filters")
    public ProductFilterMetadataDTO getFilterMetadata(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String search
    ) {

        ProductFilterMetadataDTO result = metadataService.getFilterMetadata(
                brand, categories, gender, size, color,
                minPrice, maxPrice, search
        );

        return metadataMapper.toDTO(result);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addNewProduct(@RequestBody Product product){
        Product addedProduct = productService.addProduct(product);

        System.out.println(addedProduct);
        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("product", addedProduct);
        return ResponseEntity.ok(body); // status 200
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getProductDetail(
            @PathVariable String slug,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String color
    ) {

        SingleProductDetailResponseDTO dto =
                singleProductDetailService.getProductDetailBySlug(slug, size, color);

        if (dto == null) {
            return ResponseEntity.status(404).body(
                    "No product found with slug: " + slug
            );
        }

        return ResponseEntity.ok(dto);
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
