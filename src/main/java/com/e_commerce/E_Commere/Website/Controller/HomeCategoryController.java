package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Model.Home;
import com.e_commerce.E_Commere.Website.Model.HomeCategory;
import com.e_commerce.E_Commere.Website.repository.HomeCategoryRepository;
import com.e_commerce.E_Commere.Website.service.HomeCategoryService;
import com.e_commerce.E_Commere.Website.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeCategoryController {
    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;

    @PostMapping("/home/categories")
    public ResponseEntity<Home> createHomeCategories(@RequestBody List<HomeCategory> homeCategories){
        List<HomeCategory> categories = homeCategoryService.createCategories(homeCategories);
        Home home = homeService.createHomePageData(categories);
        return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategory(){
        List<HomeCategory> categories = homeCategoryService.getAllHomeCategories();
        return ResponseEntity.ok(categories);
    }

    @PatchMapping("/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long id, @RequestBody HomeCategory homeCategory) throws Exception {
        HomeCategory updateCategory = homeCategoryService.updateHomeCategory(homeCategory,id);
        return ResponseEntity.ok(updateCategory);
    }

}
