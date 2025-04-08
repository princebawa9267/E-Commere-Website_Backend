package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Model.HomeCategory;
import com.e_commerce.E_Commere.Website.repository.HomeCategoryRepository;

import java.util.List;

public interface HomeCategoryService {
    HomeCategory createHomeCategory(HomeCategory homeCategory);
    List<HomeCategory> createCategories(List<HomeCategory> homeCategories);
    HomeCategory updateHomeCategory(HomeCategory homeCategory,Long id) throws Exception;
    List<HomeCategory> getAllHomeCategories();
}
