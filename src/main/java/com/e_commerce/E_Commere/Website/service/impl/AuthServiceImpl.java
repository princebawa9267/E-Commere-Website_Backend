package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Config.JwtProvider;
import com.e_commerce.E_Commere.Website.Domain.UserRole;
import com.e_commerce.E_Commere.Website.Model.Cart;
import com.e_commerce.E_Commere.Website.Model.Seller;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.Model.VerificationCode;
import com.e_commerce.E_Commere.Website.Response.AuthResponse;
import com.e_commerce.E_Commere.Website.request.LoginRequest;
import com.e_commerce.E_Commere.Website.request.SignupRequest;
import com.e_commerce.E_Commere.Website.repository.CartRepository;
import com.e_commerce.E_Commere.Website.repository.SellerRepository;
import com.e_commerce.E_Commere.Website.repository.UserRepository;
import com.e_commerce.E_Commere.Website.repository.VerificationCodeRepository;
import com.e_commerce.E_Commere.Website.service.AuthService;
import com.e_commerce.E_Commere.Website.service.EmailService;
import com.e_commerce.E_Commere.Website.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.ott.InvalidOneTimeTokenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;

    @Override
    public void sentLoginOtp(String email, UserRole role) throws Exception {
        String SIGINING_PREFIX = "signin_";

        if(email.startsWith(SIGINING_PREFIX)){
            email = email.substring(SIGINING_PREFIX.length());

            if(role == UserRole.USER_SELLER){
                Seller seller = sellerRepository.findByEmail(email);
                if(seller == null){
                    throw new Exception("Seller doesn't find with this account");
                }
            }

            else{
                User user = userRepository.findByEmail(email);
                if(user == null){
                    throw new Exception("User not exist with provided email");
                }
            }
        }


        VerificationCode isExist = verificationCodeRepository.findByEmail(email);
        if(isExist != null){
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "Prime Goods Otp";
        String text = "Hi user, \nYour Otp is : " + otp ;

        emailService.sendVerificationOtpEmail(email,otp,subject,text);


    }

    @Override
    public String createUser(SignupRequest req) {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());
        if(verificationCode==null || !verificationCode.getOtp().equals(req.getOtp())){
//            System.out.println("Stored OTP: " + verificationCode.getOtp());
//            System.out.println("User-entered OTP: " + req.getOtp());
            throw new InvalidOneTimeTokenException("Invalid otp..");

        }



        User user = userRepository.findByEmail(req.getEmail());

        if(user == null){
            User createUser = new User();
            createUser.setEmail(req.getEmail());
            createUser.setFullName(req.getFull_name());
            System.out.println(req.getFull_name());
            createUser.setMobile("9988776655");
//            createUser.setAddresses(req.getAddress());
            createUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.USER_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signing(LoginRequest request) throws Exception {
        String username = request.getEmail();
        String otp = request.getOtp();

        Authentication authentication = authenticate(username,otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String Token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(Token);
        authResponse.setMessage("Login Successfully");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(UserRole.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {
        final String SELLER_PREFIX = "seller_";
        Seller seller = null;
        User user;
        if(username.startsWith(SELLER_PREFIX)){
            seller = sellerRepository.findByEmail(username.substring(SELLER_PREFIX.length()));
        }
        else {
            user = userRepository.findByEmail(username);
        }
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Email id or password is incorrect");

        }

//        sentLoginOtp(username);
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(seller != null ? username.substring(SELLER_PREFIX.length()) : username);
        if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
            throw new BadCredentialsException("Invalid otp..");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
