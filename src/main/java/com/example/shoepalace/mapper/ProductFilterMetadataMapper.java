package com.example.shoepalace.mapper;

import com.example.shoepalace.responseDTO.ProductFilterMetadataDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductFilterMetadataMapper {

    public ProductFilterMetadataDTO toDTO(ProductFilterMetadataDTO result) {

        if (result == null) {
            ProductFilterMetadataDTO empty = new ProductFilterMetadataDTO();
            empty.setBrands(List.of());
            empty.setCategories(List.of());
            empty.setSizes(List.of());
            empty.setColors(List.of());
            empty.setMinPrice(BigDecimal.ZERO);
            empty.setMaxPrice(BigDecimal.ZERO);
            return empty;
        }

        ProductFilterMetadataDTO dto = new ProductFilterMetadataDTO();

        dto.setBrands(result.getBrands());
        dto.setCategories(result.getCategories());
        dto.setSizes(result.getSizes());
        dto.setColors(result.getColors());
        dto.setMinPrice(result.getMinPrice());
        dto.setMaxPrice(result.getMaxPrice());

        return dto;
    }
}
