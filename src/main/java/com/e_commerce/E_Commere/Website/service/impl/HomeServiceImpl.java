package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Domain.HomeCategorySection;
import com.e_commerce.E_Commere.Website.Model.Deal;
import com.e_commerce.E_Commere.Website.Model.Home;
import com.e_commerce.E_Commere.Website.Model.HomeCategory;
import com.e_commerce.E_Commere.Website.repository.DealRepository;
import com.e_commerce.E_Commere.Website.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final DealRepository dealRepository;

    @Override
    public Home createHomePageData(List<HomeCategory> allCategories) {

        List<HomeCategory> gridCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.GRID)
                .collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES)
                .collect(Collectors.toList());

        List<HomeCategory> electricCategories = allCategories.stream()
                .filter(homeCategory -> homeCategory.getSection() == HomeCategorySection.ELECTRIC_CATEGORIES)
                .collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream()
                .filter(homeCategory -> homeCategory.getSection() == HomeCategorySection.DEALS)
                .toList();

        List<Deal> createdDeals = new ArrayList<>();

        if(dealRepository.findAll().isEmpty()) {
            List<Deal> deals = allCategories.stream()
                    .filter(homeCategory -> homeCategory.getSection() == HomeCategorySection.DEALS)
                    .map(homeCategory -> new Deal(null, 10, homeCategory))
                    .collect(Collectors.toList());
            createdDeals = dealRepository.saveAll(deals);
        }
        else createdDeals = dealRepository.findAll();

        Home home = new Home();
        home.setGrid(gridCategories);
        home.setElectricCategories(electricCategories);
        home.setDeals(createdDeals);
        home.setShopByCategories(shopByCategories);
        home.setDealCategories(dealCategories);

        return home;
    }
}
