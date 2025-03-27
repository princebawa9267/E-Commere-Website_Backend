package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Model.User;
import com.e_commerce.E_Commere.Website.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    private ResponseEntity<User> createUserHandler(
            @RequestHeader("Authorization") String jwt)
            throws Exception{
        User user = userService.findUserByJWTToken(jwt);
        return ResponseEntity.ok(user);
    }
}
