package com.bridgelabz.workshop.model;

import com.bridgelabz.workshop.dto.StockDto;
import com.bridgelabz.workshop.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private int stockSold;
//    @JoinColumn(name = "userID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnoreProperties(value = {"applications","hibernateLazyInitializer"})
//    private User user;

    @ManyToMany
    private List<User> users;

    public void updateData(StockDto stockDto){
        this.name=stockDto.getName();
        this.description=stockDto.getDescription();
        this.price=stockDto.getPrice();
        this.quantity=stockDto.getQuantity();
    }
    public Stock(StockDto stockDto){
        this.updateData(stockDto);
    }
}
