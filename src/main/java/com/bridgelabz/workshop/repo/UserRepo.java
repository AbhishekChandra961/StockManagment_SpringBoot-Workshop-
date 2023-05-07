package com.bridgelabz.workshop.repo;

import com.bridgelabz.workshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends org.springframework.data.jpa.repository.JpaRepository<User,Integer>{
    @Query(value = "Select * from stock.user where email= :email",nativeQuery = true)
    User findByEmail(String email);
    @Query(value = "select password from stock.user where email= :email",nativeQuery = true)
    String getPassword(String email);
    @Query(value = "select verifyOtp from stock.user where email= :email",nativeQuery = true)
    boolean getVerifyOtp(String email);
    @Query(value = "select login from User where email= :email",nativeQuery = true)
    boolean getLogin(String email);
}
