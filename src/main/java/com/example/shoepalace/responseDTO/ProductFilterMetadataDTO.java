package com.example.shoepalace.responseDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductFilterMetadataDTO {
    private List<String> brands;
    private List<String> categories;
    private List<String> sizes;
    private List<String> colors;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
