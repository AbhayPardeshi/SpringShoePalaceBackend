package com.example.shoepalace.services;

import com.example.shoepalace.model.Product;
import com.example.shoepalace.responseDTO.SingleProductDetailResponseDTO;
import com.example.shoepalace.responseDTO.RatingSummaryDTO;
import com.example.shoepalace.responseDTO.VariantDTO;
import com.example.shoepalace.services.helpers.VariantSelectionHelper;
import org.jspecify.annotations.NonNull;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SingleProductDetailService {

    private final MongoTemplate mongoTemplate;
    private final VariantSelectionHelper variantHelper;

    public SingleProductDetailService(MongoTemplate mongoTemplate,
                                VariantSelectionHelper variantHelper) {
        this.mongoTemplate = mongoTemplate;
        this.variantHelper = variantHelper;
    }

    public SingleProductDetailResponseDTO getProductDetailBySlug(
            String slug,
            String selectedSize,
            String selectedColor
    ) {

        // 1. Fetch product by slug

        Query query = new Query();
        query.addCriteria(Criteria.where("slug").is(slug));

        Product product = mongoTemplate.findOne(query, Product.class);

        if (product == null) {
            return null;
        }

        // 2. Map to ProductDetailResponseDTO

        SingleProductDetailResponseDTO dto = mapToDetailDTO(product);


        // 3. Apply Variant Selection Logic

        variantHelper.applyVariantSelection(dto, selectedSize, selectedColor);

        return dto;
    }

    private SingleProductDetailResponseDTO mapToDetailDTO(Product product) {

        SingleProductDetailResponseDTO dto = new SingleProductDetailResponseDTO();

        dto.setId(product.getId());
        dto.setSlug(product.getSlug());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setDescription(product.getDescription());
        dto.setGender(product.getGender());

        dto.setCategories(product.getCategories());
        dto.setImageUrl(product.getImageUrl());

        // PRICING
        dto.setOriginalPrice(product.getProductPricing().getOriginalPrice());
        dto.setDiscountedPrice(product.getProductPricing().getDiscountedPrice());
        dto.setDiscountValue(product.getProductPricing().getDiscountValue());
        dto.setDiscountType(product.getProductPricing().getDiscountType());

        // RATING SUMMARY
        RatingSummaryDTO ratingDTO = getRatingSummaryDTO(product);

        dto.setRatingSummary(ratingDTO);

        // VARIANTS
        List<VariantDTO> variantDTOs = product.getVarients().getVariantList()
                .stream()
                .map(v -> {
                    VariantDTO vd = new VariantDTO();
                    vd.setSize(v.getSize());
                    vd.setColor(v.getColor());
                    vd.setStockQuantity(v.getStockQuantity());
                    vd.setAvailable(v.isAvailable());
                    return vd;
                })
                .collect(Collectors.toList());

        dto.setVariants(variantDTOs);

        return dto;
    }

    private static @NonNull RatingSummaryDTO getRatingSummaryDTO(Product product) {
        RatingSummaryDTO ratingDTO = new RatingSummaryDTO();
        ratingDTO.setAverageRating(product.getRatingSummary().getAverageRating());
        ratingDTO.setTotalRatings(product.getRatingSummary().getTotalRatings());
        ratingDTO.setFiveStarCount(product.getRatingSummary().getFiveStarCount());
        ratingDTO.setFourStarCount(product.getRatingSummary().getFourStarCount());
        ratingDTO.setThreeStarCount(product.getRatingSummary().getThreeStarCount());
        ratingDTO.setTwoStarCount(product.getRatingSummary().getTwoStarCount());
        ratingDTO.setOneStarCount(product.getRatingSummary().getOneStarCount());
        return ratingDTO;
    }
}
