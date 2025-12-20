package com.example.shoepalace.embedded.product;

import lombok.Data;

@Data
public class RatingSummary {
    private double averageRating = 0.0;
    private int totalRatings = 0;


    private int fiveStarCount;
    private int fourStarCount;
    private int threeStarCount;
    private int twoStarCount;
    private int oneStarCount;
}
