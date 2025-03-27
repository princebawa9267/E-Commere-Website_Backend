package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryId(String categoryId);
}
