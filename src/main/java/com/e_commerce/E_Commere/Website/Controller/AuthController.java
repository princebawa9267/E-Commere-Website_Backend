package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Domain.UserRole;
import com.e_commerce.E_Commere.Website.Model.VerificationCode;
import com.e_commerce.E_Commere.Website.Response.ApiResponse;
import com.e_commerce.E_Commere.Website.Response.AuthResponse;
import com.e_commerce.E_Commere.Website.request.LoginOtpRequest;
import com.e_commerce.E_Commere.Website.request.LoginRequest;
import com.e_commerce.E_Commere.Website.request.SignupRequest;
import com.e_commerce.E_Commere.Website.repository.UserRepository;
import com.e_commerce.E_Commere.Website.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req){
        String jwt = authService.createUser(req);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register successfully");
        res.setRole(UserRole.USER_CUSTOMER);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest req) throws Exception {
        AuthResponse authResponse = authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sent-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

        authService.sentLoginOtp(req.getEmail(),req.getRole());
        ApiResponse res = new ApiResponse();
        res.setMessage("otp sent successfully");

//        System.out.println(res.getMessage());

        return ResponseEntity.ok(res);
    }

}
