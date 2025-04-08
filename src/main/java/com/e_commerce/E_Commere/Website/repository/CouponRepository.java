package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Coupon findByCode(String code);
}
