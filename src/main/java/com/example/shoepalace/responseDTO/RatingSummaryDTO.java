package com.example.shoepalace.responseDTO;

import lombok.Data;

@Data
public class RatingSummaryDTO {
    private double averageRating;
    private int totalRatings;

    private int fiveStarCount;
    private int fourStarCount;
    private int threeStarCount;
    private int twoStarCount;
    private int oneStarCount;
}

