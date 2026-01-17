package com.example.shoepalace.services.helpers;

import com.example.shoepalace.responseDTO.SingleProductDetailResponseDTO;
import com.example.shoepalace.responseDTO.VariantDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class VariantSelectionHelper {

    public void applyVariantSelection(
            SingleProductDetailResponseDTO dto,
            String selectedSize,
            String selectedColor
    ) {

        List<VariantDTO> variants = dto.getVariants();


        // 1. Extract all available sizes + colors
        Set<String> allSizes = variants.stream()
                .map(VariantDTO::getSize)
                .collect(Collectors.toSet());

        Set<String> allColors = variants.stream()
                .map(VariantDTO::getColor)
                .collect(Collectors.toSet());

        dto.setAvailableSizes(allSizes.stream().toList());
        dto.setAvailableColors(allColors.stream().toList());


        // 2. Compute colors available for selected size

        if (selectedSize != null && !selectedSize.isEmpty()) {

            Set<String> colorsForSize = variants.stream()
                    .filter(v -> v.getSize().equals(selectedSize))
                    .map(VariantDTO::getColor)
                    .collect(Collectors.toSet());

            dto.setColorsForSelectedSize(colorsForSize.stream().toList());
        }


        // 3. Compute sizes available for selected color

        if (selectedColor != null && !selectedColor.isEmpty()) {

            Set<String> sizesForColor = variants.stream()
                    .filter(v -> v.getColor().equals(selectedColor))
                    .map(VariantDTO::getSize)
                    .collect(Collectors.toSet());

            dto.setSizesForSelectedColor(sizesForColor.stream().toList());
        }


        // 4. Find selected variant (if both selected)

        VariantDTO selectedVariant = null;

        if (selectedSize != null && !selectedSize.isEmpty() &&
                selectedColor != null && !selectedColor.isEmpty()) {

            selectedVariant = variants.stream()
                    .filter(v -> v.getSize().equals(selectedSize)
                            && v.getColor().equals(selectedColor))
                    .findFirst()
                    .orElse(null);

            dto.setSelectedVariant(selectedVariant);

            if (selectedVariant != null) {
                dto.setSelectedVariantAvailable(selectedVariant.isAvailable());
                dto.setSelectedVariantStock(selectedVariant.getStockQuantity());
            } else {
                dto.setSelectedVariantAvailable(false);
                dto.setSelectedVariantStock(0);
            }
        } else {
            // When no variant is selected on initial load
            dto.setSelectedVariant(null);
            dto.setSelectedVariantAvailable(false);
            dto.setSelectedVariantStock(0);
        }
    }
}
