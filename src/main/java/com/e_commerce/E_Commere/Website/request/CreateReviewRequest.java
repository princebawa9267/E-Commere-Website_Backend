package com.e_commerce.E_Commere.Website.request;

import lombok.Data;

import java.util.List;

@Data
public class  CreateReviewRequest {
    private String reviewText;
    private double reviewRating;
    private List<String> productImages;
}
