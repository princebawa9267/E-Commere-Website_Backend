package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Model.Home;
import com.e_commerce.E_Commere.Website.Model.HomeCategory;

import java.util.List;

public interface HomeService {
    public Home createHomePageData(List<HomeCategory> allCategories);
}
