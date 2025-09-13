package com.e_commerce.E_Commere.Website.Controller;

import com.e_commerce.E_Commere.Website.Exceptions.ProductException;
import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws ProductException{
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam(required = false) String query){
        List<Product> products = productService.searchProduct(query);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber) {

        Page<Product> products = productService.getAllProducts(
                category, brand, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber);

//        Map<String,Object> response = new HashMap<>();
//        response.put("products",products.getContent());
//        response.put("currentPage",products.getNumber());
//        response.put("totalPages",products.getTotalPages());
//        response.put("totalItems",products.getTotalElements());

        // Optionally log result
        System.out.println("Fetched products: " + products.getContent());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }



}
