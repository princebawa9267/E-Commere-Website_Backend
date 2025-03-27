package com.e_commerce.E_Commere.Website.Model;

import com.e_commerce.E_Commere.Website.Domain.AccountStatus;
import com.e_commerce.E_Commere.Website.Domain.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sellerName;

    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Embedded
    private BusinessDetails businessDetail = new BusinessDetails();

//    The @Embedded annotation in Spring Boot JPA is used to embed a reusable class inside an entity. It helps to store common fields in a separate class and reuse them in multiple entities.
    @Embedded
    private BankDetails bankDetails = new BankDetails();


//The annotation @OneToOne(cascade = CascadeType.ALL) is used in Spring Boot JPA to define a one-to-one relationship between two entities. The cascade = CascadeType.ALL means that all operations (persist, merge, remove, refresh, detach) will be applied to the related entity.

//Using CascadeType.ALL, any changes made to a User will automatically be applied to Profile:
//
//    Save User → Saves Profile automatically
//    Update User → Updates Profile automatically
//    Delete User → Deletes Profile automatically
    @OneToOne(cascade = CascadeType.ALL)
    private Address pickupAddress = new Address();

    private String GSTIN;

    private UserRole role = UserRole.USER_SELLER;

    private boolean isEmailVerified = false;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATON;


}
