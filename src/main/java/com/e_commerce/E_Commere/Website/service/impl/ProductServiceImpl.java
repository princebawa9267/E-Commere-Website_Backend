package com.e_commerce.E_Commere.Website.service.impl;

import com.e_commerce.E_Commere.Website.Exceptions.ProductException;
import com.e_commerce.E_Commere.Website.Model.Category;
import com.e_commerce.E_Commere.Website.Model.Product;
import com.e_commerce.E_Commere.Website.Model.Seller;
import com.e_commerce.E_Commere.Website.repository.CategoryRepository;
import com.e_commerce.E_Commere.Website.repository.ProductRepository;
import com.e_commerce.E_Commere.Website.request.CreateProductRequest;
import com.e_commerce.E_Commere.Website.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {

        Category category1 = categoryRepository.findByCategoryId(req.getCategory());

        if(category1==null){
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1 = categoryRepository.save(category);
        }

        Category category2 = categoryRepository.findByCategoryId(req.getCategory2());

        if(category2== null){
            Category category = new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2 = categoryRepository.save(category);
        }

        Category category3 = categoryRepository.findByCategoryId(req.getCategory3());

        if(category3 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3 = categoryRepository.save(category);
        }

        int discountedPercent = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());

        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setSellingPrice(req.getSellingPrice());
        product.setMrpPrice(req.getMrpPrice());
        product.setDiscountPercent(discountedPercent);


        return null;
    }

    private int calculateDiscountPercentage(double mrpPrice, double sellingPrice) {
        if(mrpPrice<=0){
            throw new IllegalArgumentException("Actual price must be greater than 0");
        }
        double Percentage = ((mrpPrice - sellingPrice)/mrpPrice)*100;
        return (int)Percentage;
    }


    @Override
    public void deleteProduct(Long productId) throws ProductException {
            Product product = findProductById(productId);
            productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        findProductById(productId);
        product.setId(productId);

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        return productRepository.findById(productId).orElseThrow(()-> new ProductException("product not found with id "+productId));
    }

    @Override
    public List<Product> searchProduct() {
        return List.of();
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, String minPrice, String maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
//        Specification<T> is an interface provided by Spring Data JPA that allows the creation of dynamic queries using the Criteria API
//        root → Represents the root entity (Product in this case). It helps in accessing the attributes of the entity.
//
//        query → Represents the CriteriaQuery used for query construction.
//
//        criteriaBuilder → A helper class to build conditions (WHERE, AND, OR, etc.).
        Specification<Product> spec = ((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if(category!=null){
                    Join<Product,Category>  categoryJoin = root.join("category");
                    predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"),category));
                }
                if(colors!=null && !colors.isEmpty()){
                    predicates.add(criteriaBuilder.equal(root.get("color"),colors));
                }
                //filter by size
                if (sizes != null && !sizes.isEmpty()){
                    predicates.add(criteriaBuilder.equal(root.get("sizes"),sizes));
                }
                if(minPrice != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"),minPrice));
                }
                if(maxPrice != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"),maxPrice));
                }
                if(minDiscount != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"),minDiscount));
                }
                if(stock != null){
                    predicates.add(criteriaBuilder.equal(root.get("stock"),stock));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
//        Above given is filtering features
//        Below given is sorting features
        Pageable pageable;
        if(sort !=null && !sort.isEmpty()){
            switch (sort){
                case "price_low" -> pageable = PageRequest.of(pageNumber != null ? pageNumber:0,10,Sort.by("sellingPrice").ascending());
                case "price_high" -> pageable = PageRequest.of(pageNumber !=null ? pageNumber:0,10,Sort.by("sellingPrice").descending());
                default -> pageable = PageRequest.of(pageNumber!=null ? pageNumber:0,10,Sort.unsorted());
            }
        }
        else {
            pageable = PageRequest.of(pageNumber != null ? pageNumber:0,10,Sort.unsorted());
        }
        return productRepository.findAll(spec,pageable);
    }

    @Override
    public List<Product> getProductBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }
}
