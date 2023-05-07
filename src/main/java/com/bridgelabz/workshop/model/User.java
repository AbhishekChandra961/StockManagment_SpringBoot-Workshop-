package com.bridgelabz.workshop.model;

import com.bridgelabz.workshop.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String token;
    private int otp;
    private boolean verifyOtp;
    private boolean login;
    private int stockBought;

//    @JoinColumn(name = "stockID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnoreProperties(value = {"applications","hibernateLazyInitializer"})
//    private Stock stocks;
    @ManyToMany(mappedBy = "users")
    private List<Stock> stocks;

    public void updateData(UserDto userDto){
        this.firstName=userDto.getFirstName();
        this.lastName=userDto.getLastName();
        this.email=userDto.getEmail();
        this.password=userDto.getPassword();

    }
    public User(UserDto userDto){
        this.updateData(userDto);
    }
}
