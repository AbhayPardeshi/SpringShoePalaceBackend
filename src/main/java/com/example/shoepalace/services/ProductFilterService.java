package com.example.shoepalace.services;

import com.example.shoepalace.model.Product;
import com.example.shoepalace.responseDTO.PageResponseDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductFilterService {

    private final MongoTemplate mongoTemplate;

    public ProductFilterService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public PageResponseDTO<Product> getProducts(
            String brand,
            String size,
            String color,
            List<String> categories,
            String gender,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String sort,
            int page,
            int sizePage,
            String search
    ){
        List<Criteria> criteriaList = new ArrayList<>();

        // Brand filter
        if (brand != null && !brand.isEmpty()) {
            criteriaList.add(Criteria.where("brand").is(brand));
        }

        // Category filter
        if (categories != null && !categories.isEmpty()) {
            criteriaList.add(Criteria.where("categories").in(categories));
        }


        // Only Gender
        if (gender != null && !gender.isEmpty()) {
            criteriaList.add(Criteria.where("gender").is(gender));
        }

        // Size + Color together
        if (size != null && !size.isEmpty() && color != null && !color.isEmpty()) {

            Criteria variantCriteria = new Criteria().andOperator(
                    Criteria.where("size").is(size),
                    Criteria.where("color").is(color)
            );

            criteriaList.add(
                    Criteria.where("varients.variantList")
                            .elemMatch(variantCriteria)
            );
        }

        // Only Size
        else if (size != null && !size.isEmpty()) {
            criteriaList.add(
                    Criteria.where("varients.variantList")
                            .elemMatch(Criteria.where("size").is(size))
            );
        }

        // Only Color
        else if (color != null && !color.isEmpty()) {
            criteriaList.add(
                    Criteria.where("varients.variantList")
                            .elemMatch(Criteria.where("color").is(color))
            );
        }


        // Price filter
        if (minPrice != null || maxPrice != null) {
            Criteria priceCriteria = Criteria.where("productPricing.discountedPrice");

            if (minPrice != null && maxPrice != null) {
                priceCriteria = priceCriteria.gte(minPrice).lte(maxPrice);
            } else if (minPrice != null) {
                priceCriteria = priceCriteria.gte(minPrice);
            } else {
                priceCriteria = priceCriteria.lte(maxPrice);
            }

            criteriaList.add(priceCriteria);
        }

        // Combine
        Criteria finalCriteria = new Criteria();
        if (!criteriaList.isEmpty()) {
            finalCriteria = finalCriteria.andOperator(criteriaList.toArray(new Criteria[0]));
        }

        Query query = new Query(finalCriteria);

        // Sort
        Sort sortSpec = getSort(sort);
        query.with(sortSpec);

        // PAGINATION
        int skip = page * sizePage;
        query.skip(skip);
        query.limit(sizePage);

        // COUNT QUERY (no skip/limit)
        Query countQuery = new Query(finalCriteria);
        long totalItems = mongoTemplate.count(countQuery, Product.class);

        long totalPages = (long) Math.ceil((double) totalItems / sizePage);

        List<Product> products = mongoTemplate.find(query, Product.class);

        return new PageResponseDTO<>(
                products,
                totalItems,
                totalPages,
                page,
                sizePage
        );
    }

    private Sort getSort(String sort){
        if (sort == null) {
            return Sort.by(Sort.Direction.DESC, "createdAt");  // default newest
        }

        switch (sort) {

            case "price_asc":
                return Sort.by(Sort.Direction.ASC, "productPricing.discountedPrice");

            case "price_desc":
                return Sort.by(Sort.Direction.DESC, "productPricing.discountedPrice");

            case "newest":
            default:
                return Sort.by(Sort.Direction.DESC, "createdAt");
    }
        }
}
