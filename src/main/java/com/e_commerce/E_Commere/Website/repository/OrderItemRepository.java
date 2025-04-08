package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
