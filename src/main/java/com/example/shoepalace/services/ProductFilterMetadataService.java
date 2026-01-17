package com.example.shoepalace.services;

import com.example.shoepalace.model.Product;
import com.example.shoepalace.responseDTO.ProductFilterMetadataDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductFilterMetadataService {

    private final MongoTemplate mongoTemplate;

    public ProductFilterMetadataService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ProductFilterMetadataDTO getFilterMetadata(
            String brand,
            List<String> categories,
            String gender,
            String size,
            String color,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String search
    ) {

        List<Criteria> criteriaList = new ArrayList<>();

        // BRAND
        if (brand != null && !brand.isEmpty()) {
            criteriaList.add(Criteria.where("brand").is(brand));
        }

        // CATEGORIES (multi)
        if (categories != null && !categories.isEmpty()) {
            criteriaList.add(Criteria.where("categories").in(categories));
        }

        // GENDER
        if (gender != null && !gender.isEmpty()) {
            criteriaList.add(Criteria.where("gender").is(gender));
        }

        // SIZE + COLOR (variant matching)
        if (size != null && !size.isEmpty() && color != null && !color.isEmpty()) {

            Criteria variantCriteria = new Criteria().andOperator(
                    Criteria.where("size").is(size),
                    Criteria.where("color").is(color)
            );

            criteriaList.add(
                    Criteria.where("varients.variantList")
                            .elemMatch(variantCriteria)
            );

        } else if (size != null && !size.isEmpty()) {

            criteriaList.add(
                    Criteria.where("varients.variantList")
                            .elemMatch(Criteria.where("size").is(size))
            );

        } else if (color != null && !color.isEmpty()) {

            criteriaList.add(
                    Criteria.where("varients.variantList")
                            .elemMatch(Criteria.where("color").is(color))
            );
        }

        // PRICE RANGE
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

        // SEARCH
        if (search != null && !search.isEmpty()) {
            criteriaList.add(
                    new Criteria().orOperator(
                            Criteria.where("name").regex(search, "i"),
                            Criteria.where("brand").regex(search, "i"),
                            Criteria.where("description").regex(search, "i")
                    )
            );
        }

        Criteria finalCriteria = new Criteria();
        if (!criteriaList.isEmpty()) {
            finalCriteria = finalCriteria.andOperator(criteriaList.toArray(new Criteria[0]));
        }

        // ---------------------------------------
        // Mongo Aggregation Pipeline
        // ---------------------------------------

        MatchOperation matchStage = Aggregation.match(finalCriteria);

        UnwindOperation unwindVariants = Aggregation.unwind("varients.variantList", true);

        GroupOperation groupStage = Aggregation.group()
                .addToSet("brand").as("brands")
                .addToSet("categories").as("categories")
                .addToSet("varients.variantList.size").as("sizes")
                .addToSet("varients.variantList.color").as("colors")
                .min("productPricing.discountedPrice").as("minPrice")
                .max("productPricing.discountedPrice").as("maxPrice");

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                unwindVariants,
                groupStage
        );

        AggregationResults<ProductFilterMetadataDTO> result =
                mongoTemplate.aggregate(aggregation, "products", ProductFilterMetadataDTO.class);

        return result.getUniqueMappedResult();
    }
}
