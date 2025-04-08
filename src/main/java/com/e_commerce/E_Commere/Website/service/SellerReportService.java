package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Model.Seller;
import com.e_commerce.E_Commere.Website.Model.SellerReport;
public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
