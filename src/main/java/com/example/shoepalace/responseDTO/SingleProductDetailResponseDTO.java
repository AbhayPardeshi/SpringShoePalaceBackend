package com.example.shoepalace.responseDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SingleProductDetailResponseDTO {

    private String id;
    private String slug;

    private String name;
    private String brand;
    private String description;
    private String gender;

    private List<String> categories;
    private List<String> imageUrl;

    // ---- PRICING ----
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private BigDecimal discountValue;
    private String discountType;

    // ---- RATING ----
    private RatingSummaryDTO ratingSummary;

    // ---- VARIANTS (RAW) ----
    private List<VariantDTO> variants;

    // ---- VARIANT COMPUTED DATA ----
    private List<String> availableSizes;
    private List<String> availableColors;

    private List<String> colorsForSelectedSize;
    private List<String> sizesForSelectedColor;

    private VariantDTO selectedVariant;

    private boolean isSelectedVariantAvailable;
    private int selectedVariantStock;
}
