package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    SellerReport findBySellerId(Long sellerId);
}
