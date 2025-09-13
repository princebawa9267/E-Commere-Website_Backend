package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Model.Address;
import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.repository.UserRepository;
import com.e_commerce.E_Commere.Website.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/api/users/profile")
    private ResponseEntity<User> createUserHandler(
            @RequestHeader("Authorization") String jwt)
            throws Exception{
        User user = userService.findUserByJWTToken(jwt);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/users/addresses")
    public ResponseEntity<?> addAddressToUser(@RequestHeader("Authorization") String jwt, @RequestBody Address address) {
        User user = userService.findUserByJWTToken(jwt);

        userService.addAddress(user, address);
        return ResponseEntity.ok("Address added successfully");
    }

    @GetMapping("/api/users/addresses")
    public ResponseEntity<Set<Address>> getUserAddresses(@RequestHeader("Authorization") String jwt,@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user.getAddresses());
    }
}
