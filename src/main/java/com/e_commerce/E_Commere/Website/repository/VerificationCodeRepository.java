package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long>{
    VerificationCode findByEmail(String email);
    VerificationCode findByOtp(String otp);
}
