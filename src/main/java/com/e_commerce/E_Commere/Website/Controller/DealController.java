package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Model.Deal;
import com.e_commerce.E_Commere.Website.Response.ApiResponse;
import com.e_commerce.E_Commere.Website.repository.DealRepository;
import com.e_commerce.E_Commere.Website.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
public class DealController {
    private final DealService dealService;

    @PostMapping
    public ResponseEntity<Deal> createDeals(@RequestBody Deal deals) throws Exception {
        Deal createDeals = dealService.createDeal(deals);
        return new ResponseEntity<>(createDeals, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal deal) throws Exception {
        Deal updateddeal = dealService.updateDeal(deal,id);
        return ResponseEntity.ok(updateddeal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDeals(@PathVariable Long id) throws Exception {
        dealService.deleteDeal(id);

        ApiResponse response = new ApiResponse();
        response.setMessage("Deal deleted");

        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }
}
