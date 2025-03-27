package com.e_commerce.E_Commere.Website.Model;

import com.e_commerce.E_Commere.Website.Domain.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    private PaymentMethod paymentMethod;

    private  String paymentLinkId;

    @ManyToOne
    private User user;





}
