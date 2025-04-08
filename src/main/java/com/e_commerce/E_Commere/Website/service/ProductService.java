package com.e_commerce.E_Commere.Website.service;

import com.e_commerce.E_Commere.Website.Exceptions.ProductException;
import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.Seller;
import com.e_commerce.E_Commere.Website.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req, Seller seller );
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, Product product) throws ProductException;
    Product findProductById(Long productId) throws ProductException;
    List<Product> searchProduct(String query);
    public Page<Product> getAllProducts(
            String category,
            String brand,
            String colors,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );
    List<Product> getProductBySellerId(Long sellerId);
}
