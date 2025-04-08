package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long> {
    PaymentOrder findByPaymentLinkId(String paymentId);
}
