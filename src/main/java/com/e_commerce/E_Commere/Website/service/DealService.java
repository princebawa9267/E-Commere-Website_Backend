package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Model.Deal;

import java.util.List;

public interface DealService {
    List<Deal> getDeals();
    Deal createDeal(Deal deal) throws Exception;
    Deal updateDeal(Deal deal,Long id) throws Exception;
    void deleteDeal(Long id) throws Exception;
}
