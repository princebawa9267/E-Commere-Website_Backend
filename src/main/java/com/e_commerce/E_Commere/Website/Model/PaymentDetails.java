package com.e_commerce.E_Commere.Website.Model;

import com.e_commerce.E_Commere.Website.Domain.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDetails {
    private String paymentId;
    private String rozorpayPaymentLinkId;
    private String rozorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentIdZWSP;
    private PaymentStatus status;
}
