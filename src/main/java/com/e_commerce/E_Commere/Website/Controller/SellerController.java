package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Domain.AccountStatus;
import com.e_commerce.E_Commere.Website.Exceptions.SellerException;
import com.e_commerce.E_Commere.Website.Model.Seller;
import com.e_commerce.E_Commere.Website.Model.VerificationCode;
import com.e_commerce.E_Commere.Website.Response.ApiResponse;
import com.e_commerce.E_Commere.Website.Response.AuthResponse;
import com.e_commerce.E_Commere.Website.request.LoginOtpRequest;
import com.e_commerce.E_Commere.Website.request.LoginRequest;
import com.e_commerce.E_Commere.Website.repository.VerificationCodeRepository;
import com.e_commerce.E_Commere.Website.service.AuthService;
import com.e_commerce.E_Commere.Website.service.EmailService;
import com.e_commerce.E_Commere.Website.service.SellerService;
import com.e_commerce.E_Commere.Website.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/sent/login-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

        authService.sentLoginOtp(req.getEmail(),req.getRole());
        ApiResponse res = new ApiResponse();
        res.setMessage("otp set successfully");
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSelector(@RequestBody LoginRequest req) throws Exception {
        String otp = req.getOtp();
        String email = req.getEmail();

//        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
//        if (verificationCode == null && !verificationCode.getOtp().equals(otp)){
//            throw new Exception("Wrong otp....");
//        }
    req.setEmail("seller_"+email);
    AuthResponse authResponse = authService.signing(req);

return ResponseEntity.ok(authResponse);

    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception{

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);
        System.out.println(verificationCode);
        if(verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("Wrong Otp");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail() , otp);
        return new ResponseEntity<>(seller, HttpStatus.OK);

//        if(verificationCode == null )
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception, MessagingException{
        Seller saveSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        VerificationCode isExist = verificationCodeRepository.findByEmail(seller.getEmail());
        if(isExist != null){
            verificationCodeRepository.delete(isExist);
        }
        verificationCodeRepository.save(verificationCode);

        String subject = "Prime Goods Email Verification code";
        String text = "Welcome to Prime Goods," +
                "\nYour otp is " + otp +
                "\nverify account using this link ";
        String frontend_url = "http://localhost:3000/verify-seller/";
        emailService.sendVerificationOtpEmail(seller.getEmail(),verificationCode.getOtp(),subject, text + frontend_url);
        return new ResponseEntity<>(saveSeller,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException{
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller,HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception{
        Seller seller = sellerService.getSellerProfile(jwt);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

//    @GetMapping("/report")
//    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws SellerException{
//        return null;
//    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus status){
        List<Seller> seller = sellerService.getAllSellers(status);
        return ResponseEntity.ok(seller);
    }

    @PatchMapping
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt, @RequestBody Seller seller) throws Exception{
        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception{
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

}
