package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.Review;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req, User user, Product product );
    List<Review> getReviewByProductId(Long productId);
    Review updateReview(Long reviewId,String reviewText, double rating, Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;
}
