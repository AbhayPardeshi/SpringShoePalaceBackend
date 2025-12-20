package com.example.shoepalace.embedded.product;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Pricing {
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;


    private String discountType;   // PERCENTAGE | FLAT | NONE
    private BigDecimal discountValue;

    private Instant discountStartAt;
    private Instant discountEndAt;
}
