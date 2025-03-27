package com.e_commerce.E_Commere.Website.repository;

import com.e_commerce.E_Commere.Website.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
