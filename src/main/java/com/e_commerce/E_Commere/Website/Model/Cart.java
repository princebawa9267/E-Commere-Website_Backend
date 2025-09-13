package com.e_commerce.E_Commere.Website.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

//    mappedBy = "cart":
//    This tells JPA that the relationship is bidirectional, meaning the Cart entity is aware of the CartItem entity, and vice versa.
//
//    If you remove a CartItem from the cartItems set, it will be automatically deleted from the database due to the orphanRemoval = true setting.
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true )
    private Set<CartItem> cartItems= new HashSet<>();

    private double totalSellingPrice;

    private int totalItem;

    private int totalMrpPrice;

    private int discount;

    private String coupanCode;

}
