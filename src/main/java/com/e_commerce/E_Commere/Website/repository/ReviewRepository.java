package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByProductId(Long productId);
}
