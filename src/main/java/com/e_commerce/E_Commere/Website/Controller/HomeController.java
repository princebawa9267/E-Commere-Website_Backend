package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse HomeControlHandler(){
        ApiResponse response = new ApiResponse();
        response.setMessage("Response");
        return response;
    }
}
